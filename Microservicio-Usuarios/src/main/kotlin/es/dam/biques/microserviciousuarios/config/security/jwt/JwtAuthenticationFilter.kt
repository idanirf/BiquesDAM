package es.dam.biques.microserviciousuarios.config.security.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import es.dam.biques.microserviciousuarios.dto.UserLoginDTO
import es.dam.biques.microserviciousuarios.models.User
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*

private val logger = KotlinLogging.logger {}


class JwtAuthenticationFilter(
    private val jwtTokenUtil: JwtTokenUtils,
    private val authenticationManager: AuthenticationManager
) : UsernamePasswordAuthenticationFilter() {

    override fun attemptAuthentication(req: HttpServletRequest, response: HttpServletResponse): Authentication {
        logger.info { "Triying autenticate" }

        val credentials = ObjectMapper().readValue(req.inputStream, UserLoginDTO::class.java)
        val auth = UsernamePasswordAuthenticationToken(
            credentials.username,
            credentials.password,
        )
        return authenticationManager.authenticate(auth)
    }

    override fun successfulAuthentication(
        req: HttpServletRequest?, res: HttpServletResponse, chain: FilterChain?,
        auth: Authentication
    ) {
        logger.info { "Autentication correct" }
        val user = auth.principal as User
        val token: String = jwtTokenUtil.generateToken(user)
        res.addHeader("Authorization", token)
        // Authorization
        res.addHeader("Access-Control-Expose-Headers", JwtTokenUtils.TOKEN_HEADER)
    }

    override fun unsuccessfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        failed: AuthenticationException
    ) {
        logger.info { "Autentication incorrect" }

        val error = BadCredentialsError()
        response.status = error.status
        response.contentType = "application/json"
        response.writer.append(error.toString())
    }

}

private data class BadCredentialsError(
    val timestamp: Long = Date().time,
    val status: Int = 401,
    val message: String = "User or password unauthorized.",
) {
    override fun toString(): String {
        return ObjectMapper().writeValueAsString(this)
    }
}
