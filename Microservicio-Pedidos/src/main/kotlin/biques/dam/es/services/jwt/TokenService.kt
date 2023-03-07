package biques.dam.es.services.jwt

import biques.dam.es.config.TokenConfig
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import org.koin.core.annotation.Single

/**
 * Service that provides token verification functionality using JWT library.
 * @property tokenConfig The TokenConfig object that contains the JWT configuration parameters.
 * @author BiquesDAM-Team
 */
@Single
class TokenService(
    private val tokenConfig: TokenConfig
) {
    /**
     * Creates and returns a JWTVerifier object that can be used to verify JWT tokens.
     * @return A JWTVerifier object that can be used to verify JWT tokens.
     */
    fun verifyToken(): JWTVerifier {
        return JWT.require(Algorithm.HMAC512(tokenConfig.secret))
            .withAudience(tokenConfig.audience)
            .withIssuer(tokenConfig.issuer)
            .acceptExpiresAt(tokenConfig.expiration.toLong())
            .build()
    }
}