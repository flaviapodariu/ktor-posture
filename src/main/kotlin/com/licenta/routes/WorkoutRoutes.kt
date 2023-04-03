package com.licenta.routes

import com.licenta.data.datasources.ExerciseDataSource
import com.licenta.data.datasources.WorkoutDataSource
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.createWorkout(
    workoutDataSource: WorkoutDataSource,
) {
}


fun Route.getWorkoutByUser(
    workoutDataSource: WorkoutDataSource,
    exerciseDataSource: ExerciseDataSource
) {
    get("workout") {

        val userWorkout = workoutDataSource.getWorkoutByUser(3)
        println(userWorkout)
        call.respond(
            status= HttpStatusCode.OK,
            message = userWorkout
        )
    }
}