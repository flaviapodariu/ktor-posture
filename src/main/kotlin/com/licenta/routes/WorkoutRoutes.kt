package com.licenta.routes

import com.licenta.data.datasources.ExerciseMuscleDataSource
import com.licenta.data.datasources.WorkoutDataSource
import com.licenta.data.db.UserExercise
import com.licenta.data.db.UsersExercises
import com.licenta.data.db.mappers.captureReqToCaptureEntity
import com.licenta.data.db.mappers.workoutResToUsersExercise
import com.licenta.data.models.request.CaptureReq
import com.licenta.data.models.response.WorkoutRes
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.time.LocalDate

fun Route.getWorkoutByUser(
    workoutDataSource: WorkoutDataSource,
    exerciseMuscleDataSource: ExerciseMuscleDataSource
) {
    authenticate {
        get("workout") {
            val userId = call.principal<JWTPrincipal>()!!
                .payload.getClaim("id").asString()

            val userWorkout = workoutDataSource.getWorkoutByUser(userId.toInt())
            userWorkout.forEach {
                it.targets = exerciseMuscleDataSource.getAllMuscleTargetsByExercise(it.exercise.id)
            }

            call.respond(
                status= HttpStatusCode.OK,
                message = userWorkout
            )
        }
    }

}

fun Route.insertWorkout(
    workoutDataSource: WorkoutDataSource
) {
    authenticate {
        post("workout") {
            val req = call.receiveNullable<List<UserExercise>>() ?: run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val userId = call.principal<JWTPrincipal>()!!
                .payload.getClaim("id").asString()


            val res = workoutDataSource.insertWorkout(userId.toInt(), req)

            call.respond(
                status= if(res) HttpStatusCode.Created else HttpStatusCode.Conflict,
                message= res
            )

        }
    }
}