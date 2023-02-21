package es.dam.biques.microserviciousuarios.config.security.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import es.dam.biques.microserviciousuarios.exceptions.TokenInvalidException
import es.dam.biques.microserviciousuarios.models.User
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

private val logger = KotlinLogging.logger {}

@Component
class JWTTokenUtils {

    @Value("\${jwt.secret:BiquesDam}")
    private val jwtSecret: String? = null

    @Value("\${jwt.token-expiration:3600}")
    private val jwtTimeToken = 0

    fun generateToken(user: User): String {
        logger.info { "Generate token for user: ${user.username}" }

        val tokenExpirationDate = Date(System.currentTimeMillis() + jwtTimeToken * 1000)

        return JWT.create()
            .withSubject(user.uuid.toString())
            .withIssuedAt(Date())
            .withExpiresAt(tokenExpirationDate)
            .withClaim("username", user.username)
            .withClaim("types", user.type.split(",").toSet().toString())
            .sign(Algorithm.HMAC512(jwtSecret))
    }

    fun getUserIdFromJwt(token: String?): String {
        logger.info { "Getting the user ID: $token" }

        return validateToken(token!!)!!.subject
    }

    fun validateToken(authToken: String): DecodedJWT? {
        logger.info { "Validate the token: $authToken" }

        try {
            return JWT.require(Algorithm.HMAC512(jwtSecret)).build().verify(authToken)
        } catch (e: Exception) {
            throw TokenInvalidException("Invalid or expired token")
        }
    }

    private fun getClaimsFromJwt(token: String) = validateToken(token)?.claims

    fun getUsernameFromJwt(token: String): String {
        logger.info { "Getting username from token: $token" }

        val claims = getClaimsFromJwt(token)
        return claims!!["username"]!!.asString()
    }

    fun getRolesFromJwt(token: String): String {
        logger.info { "Getting the roles from the token: $token" }

        val claims = getClaimsFromJwt(token)
        return claims!!["types"]!!.asString()
    }

    fun isTokenValid(token: String): Boolean {
        logger.info { "Checking if the token is valid: $token" }

        val claims = getClaimsFromJwt(token)!!
        val expirationDate = claims["exp"]!!.asDate()
        val now = Date(System.currentTimeMillis())

        return now.before(expirationDate)
    }
}