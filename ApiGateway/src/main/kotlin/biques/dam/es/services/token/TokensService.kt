package biques.dam.es.services.token

import biques.dam.es.config.TokenConfig
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.auth.jwt.*
import org.koin.core.annotation.Single
import java.util.*

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
