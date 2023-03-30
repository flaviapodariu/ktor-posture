package com.licenta.plugins

import ch.qos.logback.core.subst.Token
import com.licenta.data.datasources.CaptureDataSource
import com.licenta.data.datasources.ExerciseDataSource
import com.licenta.data.datasources.UserDataSource
import com.licenta.data.datasources.WorkoutDataSource
import com.licenta.routes.*
import com.licenta.security.HashingService
import com.licenta.security.jwt.JwtTokenService
import com.licenta.security.jwt.TokenConfig
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    val hashingService by inject<HashingService>()
    val captureDataSource by inject<CaptureDataSource>()
    val userDataSource by inject<UserDataSource>()
    val exerciseDataSource by inject<ExerciseDataSource>()
    val workoutDataSource by inject<WorkoutDataSource>()
    val tokenService by inject<JwtTokenService>()
    val tokenConfig by inject<TokenConfig>()

    routing {
        register(hashingService, userDataSource, tokenService, tokenConfig)
        login(userDataSource, hashingService, tokenService, tokenConfig)
        checkAuthOnStart()
        getUserCaptures(captureDataSource)
        insertCapture(captureDataSource)
        getWorkoutByUser(workoutDataSource, exerciseDataSource)

    }
}
