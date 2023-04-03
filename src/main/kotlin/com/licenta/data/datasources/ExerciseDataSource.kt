package com.licenta.data.datasources

import com.licenta.data.db.ExerciseEntity
import com.licenta.data.models.Exercise

interface ExerciseDataSource {

    suspend fun getExerciseById(exerciseId: Int) : Exercise?
    suspend fun getAllByLordosisDesc() : List<ExerciseEntity>
    suspend fun getLordosisWhereScoreAbove(score: Int) : List<ExerciseEntity>
    suspend fun getAllByRoundedShouldersDesc() : List<ExerciseEntity>
    suspend fun getRoundedShouldersWhereScoreAbove(score: Int) : List<ExerciseEntity>
    suspend fun getAllByHeadForwardDesc() : List<ExerciseEntity>
    suspend fun getHeadForwardWhereScoreAbove(score: Int) : List<ExerciseEntity>
}