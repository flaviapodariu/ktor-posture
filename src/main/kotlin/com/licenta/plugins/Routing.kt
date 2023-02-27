package com.licenta.plugins

import com.licenta.data.models.datasources.UserDataSource
import com.licenta.routes.checkAuthOnStart
import com.licenta.routes.login
import com.licenta.routes.register
import com.licenta.security.HashingService
import com.licenta.security.jwt.JwtTokenService
import com.licenta.security.jwt.TokenConfig
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    hashingService: HashingService,
    userDataSource: UserDataSource,
    tokenService: JwtTokenService,
    tokenConfig: TokenConfig

) {
    routing {
        register(hashingService, userDataSource, tokenService, tokenConfig)
        login(userDataSource, hashingService, tokenService, tokenConfig)
        checkAuthOnStart()

    }
}
