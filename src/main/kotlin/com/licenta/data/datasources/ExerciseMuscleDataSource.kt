package com.licenta.data.datasources

import com.licenta.data.models.Exercise
import com.licenta.data.models.response.ExerciseMuscleType

interface ExerciseMuscleDataSource {

    suspend fun  getAllMuscleTargetsByExercise(exerciseId: Int) : List<ExerciseMuscleType>
    suspend fun getExercisesByMuscleAndType(muscleId: Int, type: String) : List<Exercise>
}