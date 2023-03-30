package com.licenta

import com.licenta.data.datasources.*
import com.licenta.plugins.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.util.reflect.*

fun main(args: Array<String>): Unit =
        io.ktor.server.netty.EngineMain.main(args)


@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    System.setProperty("kotlinx.serialization.debug", "true")

    configureKoin()
    configureSecurity()
    configureMonitoring()
    configureSerialization()
    configureHTTP()
    configureRouting()
}


