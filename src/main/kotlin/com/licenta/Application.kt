package com.licenta

import com.licenta.data.models.datasources.HistoryDataSourceImpl
import com.licenta.data.models.datasources.UserDataSourceImpl
import com.licenta.plugins.*
import com.licenta.security.HashingServiceImpl
import com.licenta.security.jwt.JwtTokenService
import com.licenta.security.jwt.TokenConfig
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.util.reflect.*
import org.ktorm.database.Database

fun main(args: Array<String>): Unit =
        io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    System.setProperty("kotlinx.serialization.debug", "true")
    val url = environment.config.property("database.url").getString()
    val user = environment.config.property("database.user").getString()
    val pass = System.getenv("DB_PASSWORD")
    val db = Database.connect(
        url = url,
        user = user,
        password = pass
    )

    val historyDataSource = HistoryDataSourceImpl(db)
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
    configureRouting(hashingService, historyDataSource, userDataSource, tokenService, tokenConfig)
}
