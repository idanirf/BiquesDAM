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
    /**
     * Generates a JSON Web Token (JWT) for a given user with a set of claims.
     * @param user The user for whom the token will be generated.
     * @return The generated JWT as a String.
     * @author BiquesDAM-Team
     */
    fun generateToken(user: User): String {
        logger.info { "Generate token for user: ${user.username}" }

        return JWT.create()
            .withSubject(user.uuid.toString())
            .withIssuer("BiquesUsuarios")
            .withAudience("biquesdam")
            .withExpiresAt(Date(System.currentTimeMillis() + (60 * 60 * 1000)))
            .withClaim("username", user.username)
            .withClaim("rol", user.rol.split(",").toSet().toString())
            .sign(Algorithm.HMAC512("biquesdam"))
    }

    /**
     * Verifies the validity of a given JWT.
     * @param authToken The JWT to be verified.
     * @return The decoded JWT if it is valid, null otherwise.
     * @author BiquesDAM-Team
     */
    fun verify(authToken: String): DecodedJWT? {
        logger.info { "Validating the token: $authToken" }

        return try {
            JWT.require(Algorithm.HMAC512("biquesdam")).build().verify(authToken)
        } catch (e: Exception) {
            null
        }
    }
}