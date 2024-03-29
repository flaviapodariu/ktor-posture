package com.licenta.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.licenta.security.jwt.TokenConfig
import io.ktor.server.application.*
import org.koin.ktor.ext.inject

fun Application.configureSecurity() {

    val config by inject<TokenConfig>()
    authentication {
        jwt {
            realm = this@configureSecurity.environment.config.property("jwt.realm").getString()
            verifier(
                    JWT
                            .require(Algorithm.HMAC256(config.secret))
                            .withAudience(config.audience)
                            .withIssuer(config.issuer)
                            .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(config.audience))
                    JWTPrincipal(credential.payload)   
                else null
            }
        }
    }
}
