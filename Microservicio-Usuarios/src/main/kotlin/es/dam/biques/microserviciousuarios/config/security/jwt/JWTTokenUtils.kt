package es.dam.biques.microserviciousuarios.config.security.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import es.dam.biques.microserviciousuarios.models.User
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.util.*

private val logger = KotlinLogging.logger {}

@Component
class JWTTokenUtils {
    fun generateToken(user: User): String {
        logger.info { "Generate token for user: ${user.username}" }

        return JWT.create()
            .withSubject(user.uuid.toString())
            .withIssuer("BiquesUsuarios")
            .withExpiresAt(Date(System.currentTimeMillis() + (60 * 60 * 1000)))
            .withClaim("username", user.username)
            .withClaim("rol", user.rol.split(",").toSet().toString())
            .sign(Algorithm.HMAC512("BiquesDAM"))
    }

    fun verify(authToken: String): DecodedJWT? {
        logger.info { "Validating the token: $authToken" }

        return try {
            JWT.require(Algorithm.HMAC512("BiquesDAM")).build().verify(authToken)
        } catch (e: Exception) {
            null
        }
    }
}