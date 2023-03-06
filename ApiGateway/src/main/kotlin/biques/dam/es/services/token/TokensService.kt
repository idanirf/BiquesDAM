package biques.dam.es.services.token

import biques.dam.es.config.TokenConfig
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import org.koin.core.annotation.Single

@Single
class TokensService(
    private val tokenConfig: TokenConfig
) {
    fun verifyJWT(): JWTVerifier {
        return JWT.require(Algorithm.HMAC512(tokenConfig.secret))
            //.withAudience(tokenConfig.audience)
            .withIssuer(tokenConfig.issuer)
            .build()
    }
}
