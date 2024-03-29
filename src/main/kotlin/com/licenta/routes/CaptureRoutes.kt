package com.licenta.routes

import com.licenta.data.datasources.CaptureDataSource
import com.licenta.data.datasources.ExerciseDataSource
import com.licenta.data.datasources.ExerciseMuscleDataSource
import com.licenta.data.datasources.WorkoutDataSource
import com.licenta.data.db.mappers.usersExerciseToWorkoutRes
import com.licenta.data.models.request.CaptureReq
import com.licenta.data.util.recommendWorkout
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getUserCaptures(
    captureDataSource: CaptureDataSource
) {
    authenticate {
        get("dashboard") {
            val id = call.principal<JWTPrincipal>()!!
                .payload.getClaim("id").asString()

            //what if user does not exist?
            val userHistory = captureDataSource.getAllCaptures(id.toInt())

            call.respond(
                status = HttpStatusCode.OK,
                message = userHistory
            )
            return@get

        }
    }

}

fun Route.insertCapture(
    captureDataSource: CaptureDataSource,
    exerciseMuscleDataSource: ExerciseMuscleDataSource,
    workoutDataSource: WorkoutDataSource
) {
    authenticate{
        post("dashboard") {
           val req = call.receiveNullable<CaptureReq>() ?: run {
                call.respond(HttpStatusCode.BadRequest) // req json could not be mapped to register request model
                return@post
            }
            val id = call.principal<JWTPrincipal>()!!
                .payload.getClaim("id").asString()

            if (!captureDataSource.insertCapture(id.toInt(), req)) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message= false
                )
                return@post
            }

            val workout = recommendWorkout(req, exerciseMuscleDataSource)
            workoutDataSource.insertWorkout(id.toInt(), workout).also { println(it) }

            call.respond(
                status = HttpStatusCode.Created,
                message = true
            )
            return@post
        }

    }
}

fun Route.createWorkoutFromCapture(
    exerciseMuscleDataSource: ExerciseMuscleDataSource
) {
    post("dashboard/visitor"){
        val req = call.receiveNullable<CaptureReq>() ?: run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val workout = recommendWorkout(req, exerciseMuscleDataSource)

        val res = workout.map {
            usersExerciseToWorkoutRes(it)
        }
        res.forEach {
            it.targets = exerciseMuscleDataSource.getAllMuscleTargetsByExercise(it.exercise.id)
        }

        call.respond(
            status = HttpStatusCode.OK,
            message = res
        )
        return@post
    }
}