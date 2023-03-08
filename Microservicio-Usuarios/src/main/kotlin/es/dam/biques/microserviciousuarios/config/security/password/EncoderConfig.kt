package es.dam.biques.microserviciousuarios.config.security.password

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


@Configuration
class EncoderConfig {

    /**
     * Defines a bean for the password encoder used in the application.
     * @return an instance of the [BCryptPasswordEncoder] class.
     * @author BiquesDAM-Team
     */
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}