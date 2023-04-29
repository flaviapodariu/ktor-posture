package com.licenta.routes

import com.licenta.data.datasources.ExerciseDataSource
import com.licenta.data.datasources.WorkoutDataSource
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getWorkoutByUser(
    workoutDataSource: WorkoutDataSource,
    exerciseDataSource: ExerciseDataSource
) {
    authenticate {
        get("workout") {
            val userId = call.principal<JWTPrincipal>()!!
                .payload.getClaim("id").asString()

            val userWorkout = workoutDataSource.getWorkoutByUser(userId.toInt())

            call.respond(
                status= HttpStatusCode.OK,
                message = userWorkout
            )
        }
    }

}