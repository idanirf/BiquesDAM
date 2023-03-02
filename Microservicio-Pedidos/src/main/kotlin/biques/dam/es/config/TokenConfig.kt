package biques.dam.es.config

import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.Single

@Single
data class TokenConfig(
    @InjectedParam private val config: Map<String, String>
) {
    val audience = config["audience"].toString()
    val secret = config["secret"].toString()
    val issuer = config["issuer"].toString()
}
