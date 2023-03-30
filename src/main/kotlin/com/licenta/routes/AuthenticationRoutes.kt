package com.licenta.routes

import com.licenta.data.db.User
import com.licenta.data.models.request.LoginReq
import com.licenta.data.models.request.RegisterReq
import com.licenta.data.models.response.AuthRes
import com.licenta.data.datasources.UserDataSource
import com.licenta.security.HashingService
import com.licenta.security.SaltedHash
import com.licenta.security.jwt.JwtTokenService
import com.licenta.security.jwt.TokenClaim
import com.licenta.security.jwt.TokenConfig
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.register(
    hashingService: HashingService,
    userDataSource: UserDataSource,
    tokenService: JwtTokenService,
    tokenConfig: TokenConfig
) {
    post("register") {
        val req = call.receiveNullable<RegisterReq>() ?: run {
            call.respond(HttpStatusCode.BadRequest) // req json could not be mapped to register request model
            return@post
        }
        if(userDataSource.getUserByEmail(req.email) != null) {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }

        //other exceptions
        val saltedHash = hashingService.generateSaltedHash(req.password)
        val user =  User{
            email = req.email
            nickname = req.nickname
            password = saltedHash.hash
            salt = saltedHash.salt
    }

        val created = userDataSource.createUser(user)
        if(!created) {
            call.respond(HttpStatusCode.Conflict)
        }

        val token = tokenService.generate(
            tokenConfig,
            TokenClaim("id", "${user.id}"),
            TokenClaim("nickname", user.nickname)
        )

        call.respond(
            status= HttpStatusCode.Created,
            message= AuthRes(token, user.nickname))
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
        if(!hashingService.verify(req.password, saltedHash)) {
            call.respond(HttpStatusCode.Unauthorized)
            return@post
        }

        val token = tokenService. generate(
            tokenConfig,
            TokenClaim("id", "${user.id}"),
            TokenClaim("nickname", user.nickname)
            )
        call.respond(status=HttpStatusCode.OK,
            message = AuthRes(token, user.nickname))
        return@post
}

}

// if token is not valid authenticate handles, else respond ok
fun Route.checkAuthOnStart() {
    authenticate {
        get {
            val nickname = call.principal<JWTPrincipal>()!!
                .payload.getClaim("nickname").asString()

            println("nickn: ${nickname}")
            call.respond(
                HttpStatusCode.OK,
                message= nickname)
        }
    }
}