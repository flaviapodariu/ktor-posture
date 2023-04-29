package com.licenta.data.util

import com.licenta.data.datasources.ExerciseDataSource
import com.licenta.data.db.ExerciseEntity
import com.licenta.data.db.UserExercise
import com.licenta.data.models.request.CaptureReq

suspend fun recommendWorkout(
    userCapture: CaptureReq,
    exerciseDataSource: ExerciseDataSource
) : List<UserExercise> {

    val workout = mutableListOf<UserExercise>()
    val exerciseList = mutableListOf<ExerciseEntity>()

    return workout
}
