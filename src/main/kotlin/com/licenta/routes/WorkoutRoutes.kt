package com.licenta.routes

import com.licenta.data.datasources.ExerciseDataSource
import com.licenta.data.datasources.UserDataSource
import com.licenta.data.datasources.WorkoutDataSource
import com.licenta.data.db.UsersExercise
import com.licenta.data.services.WorkoutResolver
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.insertWorkout(
    workoutResolver: WorkoutResolver,
    workoutDataSource: WorkoutDataSource
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