package com.licenta.routes

import com.licenta.data.datasources.ExerciseDataSource
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getExerciseById(
    exerciseDataSource: ExerciseDataSource
) {
    get("exercises/{exerciseId}") {

        call.parameters["exerciseId"]?.let {
            val exercise = exerciseDataSource.getExerciseById(it.toInt())
            if(exercise == null) {
                call.respond(HttpStatusCode.NotFound)
                return@get
            }

            call.respond(
                status = HttpStatusCode.OK,
                message = exercise
            )
            return@get
        }
    }
}