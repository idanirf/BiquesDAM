package es.dam.biques.microserviciousuarios.config.security.jwt

import es.dam.biques.microserviciousuarios.service.UserService
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kotlinx.coroutines.runBlocking
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException

class JWTAuthorizationFilter(
    private val jwtTokenUtil: JWTTokenUtils,
    private val service: UserService,
    authManager: AuthenticationManager,
) : BasicAuthenticationFilter(authManager) {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
        req: HttpServletRequest,
        res: HttpServletResponse,
        chain: FilterChain
    ) {
        logger.info { "Filtrando" }
        val header = req.getHeader(AUTHORIZATION.toString())
        if (header == null) {
            chain.doFilter(req, res)
            return
        }
        getAuthentication(header.substring(7))?.also {
            SecurityContextHolder.getContext().authentication = it
        }
        chain.doFilter(req, res)
    }

    private fun getAuthentication(token: String): UsernamePasswordAuthenticationToken? = runBlocking {
        logger.info { "Obteniendo autenticación" }
        val tokenDecoded = jwtTokenUtil.verify(token) ?: return@runBlocking null

        val username = tokenDecoded.getClaim("username").toString().replace("\"", "")

        val user = service.loadUserByUsername(username)

        return@runBlocking UsernamePasswordAuthenticationToken(
            user,
            null,
            user.authorities
        )
    }
}