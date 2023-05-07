package com.licenta.data.datasources

import com.licenta.data.db.ExerciseEntity
import com.licenta.data.models.response.ExerciseMuscleType

interface ExerciseMuscleDataSource {

    suspend fun  getAllMuscleTargetsByExercise(exerciseId: Int) : List<ExerciseMuscleType>
    suspend fun getExercisesByMuscleNameAndType(muscleName: String, type: String) : List<ExerciseEntity>
    
}