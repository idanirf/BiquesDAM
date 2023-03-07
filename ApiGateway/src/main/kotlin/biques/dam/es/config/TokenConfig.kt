package biques.dam.es.config

import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.Single

/**
 * This data class represents a configuration for JWT token authentication.
 * @property config a map of key-value pairs containing the configuration properties for the JWT token.
 * @property audience a string representing the intended audience for the JWT token.
 * @property secret a string representing the secret key used to sign and verify the JWT token.
 * @property issuer a string representing the issuer of the JWT token.
 * @property realm a string representing the security realm for the JWT token.
 * @author BiquesDAM-Team
 */
@Single
data class TokenConfig(
    @InjectedParam private val config: Map<String, String>
) {
    val audience = config["audience"].toString()
    val secret = config["secret"].toString()
    val issuer = config["issuer"].toString()
    val realm = config["realm"].toString()
}