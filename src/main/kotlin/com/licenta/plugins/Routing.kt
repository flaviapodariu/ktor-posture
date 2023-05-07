package com.licenta.plugins

import com.licenta.data.datasources.*
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
    val exerciseMuscleDataSource by inject<ExerciseMuscleDataSource>()
    val workoutDataSource by inject<WorkoutDataSource>()
    val tokenService by inject<JwtTokenService>()
    val tokenConfig by inject<TokenConfig>()

    routing {
        register(hashingService, userDataSource, tokenService, tokenConfig)
        login(userDataSource, hashingService, tokenService, tokenConfig)
        checkAuthOnStart()
        getUserCaptures(captureDataSource)
        insertCapture(captureDataSource, exerciseMuscleDataSource, workoutDataSource)
        getWorkoutByUser(workoutDataSource, exerciseMuscleDataSource)
        getExerciseById(exerciseDataSource)

    }
}
