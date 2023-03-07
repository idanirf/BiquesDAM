package es.dam.biques.microserviciousuarios.config.security.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import es.dam.biques.microserviciousuarios.dto.UserLoginDTO
import es.dam.biques.microserviciousuarios.models.User
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*

class JWTAuthenticationFilter(
    private val jwtTokenUtil: JWTTokenUtils,
    private val authenticationManager: AuthenticationManager
) : UsernamePasswordAuthenticationFilter() {
    /**
     * Overrides the default attemptAuthentication method of the parent class to authenticate a user by their credentials
     * @param req The HttpServletRequest object that contains the user's request information.
     * @param response The HttpServletResponse object that contains the response information to be sent back to the user.
     * @return An instance of the Authentication interface that represents the authentication of the user.
     * @author BiquesDAM-Team
     */
    @OptIn(ExperimentalSerializationApi::class)
    override fun attemptAuthentication(req: HttpServletRequest, response: HttpServletResponse): Authentication {
        logger.info { "Triying to authenticate..." }

        val credentials = Json.decodeFromStream<UserLoginDTO>(req.inputStream)
        val auth = UsernamePasswordAuthenticationToken(
            credentials.username,
            credentials.password,
        )
        return authenticationManager.authenticate(auth)
    }

    /**
    Overrides the default successfulAuthentication method of the parent class to handle successful user authentication.
     * @param req The HttpServletRequest object that contains the user's request information.
     * @param res The HttpServletResponse object that contains the response information to be sent back to the user.
     * @param chain The FilterChain object that manages the filter processing of the request.
     * @param auth An instance of the Authentication interface that represents the authentication of the user.
     * @author BiquesDAM-Team
     */
    override fun successfulAuthentication(
        req: HttpServletRequest?, res: HttpServletResponse, chain: FilterChain?,
        auth: Authentication
    ) {
        logger.info { "Autentication is correct!" }

        val user = auth.principal as User
        val token: String = jwtTokenUtil.generateToken(user)
        res.addHeader("Authorization", token)
    }

    /**
     * Overrides the default unsuccessfulAuthentication method of the parent class to handle failed user authentication.
     * @param request The HttpServletRequest object that contains the user's request information.
     * @param response The HttpServletResponse object that contains the response information to be sent back to the user.
     * @param failed An instance of the AuthenticationException interface that represents the reason for the authentication failure.
     * @author BiquesDAM-Team
     */
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
    val message: String = "User or password incorrect.",
) {
    /**
     * Returns a JSON string representation of the object using the Jackson ObjectMapper.
     * @return A JSON string representing the object.
     * @author BiquesDAM-Team
     */
    override fun toString(): String {
        return ObjectMapper().writeValueAsString(this)
    }
}
