package com.licenta.data.datasources

import com.licenta.data.models.Exercise

interface ExerciseMuscleDataSource {
    suspend fun getExercisesByMuscleAndType(muscleId: Int, type: String) : List<Exercise>
}