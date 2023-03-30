package com.licenta.data.datasources

import com.licenta.data.db.ExerciseEntity
import com.licenta.data.models.Exercise

interface ExerciseDataSource {

    suspend fun getExerciseById(exerciseId: Int) : Exercise?
    suspend fun getAllLordosisByScoreDesc() : List<ExerciseEntity>
    suspend fun getAllRoundedShouldersByScoreDesc() : List<Exercise>
    suspend fun getAllHeadForwardByScoreDesc() : List<Exercise>
}