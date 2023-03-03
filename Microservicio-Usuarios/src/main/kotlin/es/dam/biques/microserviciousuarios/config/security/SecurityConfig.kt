package es.dam.biques.microserviciousuarios.config.security

import es.dam.biques.microserviciousuarios.config.security.jwt.JWTAuthenticationFilter
import es.dam.biques.microserviciousuarios.config.security.jwt.JWTAuthorizationFilter
import es.dam.biques.microserviciousuarios.config.security.jwt.JWTTokenUtils
import es.dam.biques.microserviciousuarios.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
class SecurityConfig
@Autowired constructor(
    private val userService: UserService,
    private val jwtTokenUtils: JWTTokenUtils
) {
    @Bean
    fun authManager(http: HttpSecurity): AuthenticationManager {
        val authenticationManagerBuilder = http.getSharedObject(
            AuthenticationManagerBuilder::class.java
        )

        authenticationManagerBuilder.userDetailsService(userService)
        return authenticationManagerBuilder.build()
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        val authenticationManager = authManager(http)

        http
            .csrf()
            .disable()
            .exceptionHandling()
            .and()

            .authenticationManager(authenticationManager)
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            .authorizeHttpRequests()
            .requestMatchers("/error/**").permitAll()
            .requestMatchers("/users/login", "/users/register").permitAll()
            .requestMatchers("/**").permitAll()
            .requestMatchers("/users", "/users{id}").hasAnyRole("ADMIN", "SUPERADMIN")
            .anyRequest().authenticated()

            .and()
            .addFilter(JWTAuthenticationFilter(jwtTokenUtils, authenticationManager))
            .addFilter(JWTAuthorizationFilter(jwtTokenUtils, userService, authenticationManager))

        return http.build()
    }
}