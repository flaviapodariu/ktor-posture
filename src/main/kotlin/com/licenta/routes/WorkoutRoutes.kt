package com.licenta.routes

import com.licenta.data.models.datasources.UserDataSource
import com.licenta.data.services.WorkoutResolver
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Route.sendPostureResults(
    workoutResolver: WorkoutResolver,
    userDataSource: UserDataSource
) {


}