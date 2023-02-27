package com.licenta

import com.licenta.data.models.datasources.UserDataSourceImpl
import io.ktor.server.application.*
import com.licenta.plugins.*
import com.licenta.security.HashingServiceImpl
import com.licenta.security.jwt.JwtTokenService
import com.licenta.security.jwt.TokenConfig
import io.ktor.util.reflect.*
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

fun main(args: Array<String>): Unit =
        io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    val dbPassword = System.getenv("DB_PASSWORD")
    val dbName = "ktor-posture"
    val db = KMongo.createClient(
        connectionString = "mongodb+srv://flaviapodariu:$dbPassword@postureimprover.bd0pdk4.mongodb.net/$dbName?retryWrites=true&w=majority"
    ).coroutine.getDatabase(dbName)

    val userDataSource = UserDataSourceImpl(db)
    val tokenService = JwtTokenService()
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expiresIn = 60L * 60L * 24L * 30L * 1000L, //60sec* 60 min * 24h * 30 days * 1000ms => 30 days in ms
        secret = System.getenv("JWT_SECRET")
    )

    val hashingService = HashingServiceImpl()


    configureSecurity(tokenConfig)
    configureMonitoring()
    configureSerialization()
    configureHTTP()
    configureRouting(hashingService, userDataSource, tokenService, tokenConfig)
}
