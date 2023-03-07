package biques.dam.es.services.token

import biques.dam.es.config.TokenConfig
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.auth.jwt.*
import org.koin.core.annotation.Single

/**
 * Service that generates and verifies JWT tokens
 * @param tokenConfig The configuration of the tokens
 * @author The BiquesDAM Team
 */
@Single
class TokensService(
    private val tokenConfig: TokenConfig) {

    /**
     * Generates a JWT token
     * @return The generated token
     * @author The BiquesDAM Team
     */
    fun verifyJWT(): JWTVerifier {
        return JWT.require(Algorithm.HMAC512(tokenConfig.secret))
            .withIssuer(tokenConfig.issuer)
            .build()
    }

    /**
     * Generates a JWT token
     * @param token The token to generate
     * @return The generated token as a String
     * @author The BiquesDAM Team
     */
    fun generateToken(token: JWTPrincipal): String{
            return JWT.create()
                .withIssuer(token.payload.issuer)
                .withSubject(token.payload.subject)
                .withClaim("username", token.payload.getClaim("username").toString().replace("\"", ""))
                .withClaim("rol", token.payload.getClaim("rol").toString().replace("\"", ""))
                .withExpiresAt(token.payload.expiresAt)
                .sign(Algorithm.HMAC512("BiquesDAM"))
    }
}
