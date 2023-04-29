package com.licenta.data.db.mappers

import com.licenta.data.db.ExerciseEntity
import com.licenta.data.db.UserExercise
import com.licenta.data.models.Exercise
import com.licenta.data.models.response.WorkoutRes

fun exerciseEntityToExercise(entity: ExerciseEntity) : Exercise {
    return Exercise(
        id = entity.id,
        name = entity.name,
        description = entity.description,
        imageUrl = entity.imageUrl
    )
}
fun usersExerciseToWorkoutRes(userEx: UserExercise) : WorkoutRes{
    return WorkoutRes(
        exercise = exerciseEntityToExercise(userEx.exercise),
        reps = userEx.reps
    )
}


