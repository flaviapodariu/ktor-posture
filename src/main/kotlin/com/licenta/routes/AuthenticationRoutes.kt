package com.licenta.routes

import com.licenta.data.models.User
import com.licenta.data.models.request.LoginReq
import com.licenta.data.models.request.RegisterReq
import com.licenta.data.models.response.AuthenticationRes
import com.licenta.data.models.datasources.user.UserDataSource
import com.licenta.security.HashingService
import com.licenta.security.SaltedHash
import com.licenta.security.jwt.JwtTokenService
import com.licenta.security.jwt.TokenClaim
import com.licenta.security.jwt.TokenConfig
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.register(
    hashingService: HashingService,
    userDataSource: UserDataSource
) {
    post("register") {
        val req = call.receiveNullable<RegisterReq>() ?: run {
            call.respond(HttpStatusCode.BadRequest) // req json could not be mapped to register request model
            return@post
        }

        //other exceptions
        val saltedHash = hashingService.generateSaltedHash(req.password)
        val user =  User(
            email = req.email,
            nickname = req.nickname,
            password = saltedHash.hash,
            salt = saltedHash.salt
        )

        val created = userDataSource.createUser(user)
        if(!created) {
            call.respond(HttpStatusCode.Conflict)
        }

        call.respond(HttpStatusCode.Created)
    }
}
fun Route.login(
    userDataSource: UserDataSource,
    hashingService: HashingService,
    tokenService: JwtTokenService,
    tokenConfig: TokenConfig
) {
    post("login") {
        val req = call.receiveNullable<LoginReq>() ?: run {
            call.respond(HttpStatusCode.BadRequest) // req json could not be mapped to register request model
            return@post
        }

        val user = userDataSource.getUserByEmail(req.email)
        if(user == null) {
           call.respond(HttpStatusCode.NotFound)
            return@post
        }

        val saltedHash = SaltedHash(user.salt, user.password)
        if(!hashingService.verify(user.password, saltedHash)) {
            call.respond(HttpStatusCode.Unauthorized)
            return@post
        }

        val token = tokenService.generate(
            tokenConfig,
            TokenClaim("email", user.email),
            TokenClaim("nickname", user.nickname)
            )
        call.respond(status=HttpStatusCode.OK,
            message = AuthenticationRes(token))
}

    // if token is not valid authenticate handles, else respond ok
    fun Route.checkAuthOnStart() {
            authenticate {
                get {
                    call.respond(HttpStatusCode.OK)
                }
            }
    }

}