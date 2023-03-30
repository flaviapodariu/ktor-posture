package com.licenta.data.db.mappers

import com.licenta.data.db.ExerciseEntity
import com.licenta.data.db.UsersExercise
import com.licenta.data.models.Exercise
import com.licenta.data.models.response.WorkoutRes

fun exerciseEntityToExercise(entity: ExerciseEntity) : Exercise {
    return Exercise(
        id = entity.id,
        name = entity.name,
        description = entity.description,
        lordosisScore = entity.lordosisScore,
        headFwdScore = entity.headFwdScore,
        roundedShScore = entity.roundedShScore
    )
}
fun usersExerciseToWorkoutRes(userEx: UsersExercise) : WorkoutRes{
    return WorkoutRes(
        exercise = exerciseEntityToExercise(userEx.exercise),
        reps = userEx.reps
    )
}


