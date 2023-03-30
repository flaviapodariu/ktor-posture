package com.licenta.data.datasources

import com.licenta.data.db.ExerciseEntity
import com.licenta.data.db.exercises
import com.licenta.data.db.mappers.exerciseEntityToExercise
import com.licenta.data.models.Exercise
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.find
import org.ktorm.entity.map
import org.ktorm.entity.sortedByDescending
import org.ktorm.entity.toList

class ExerciseDataSourceImpl(
    db: Database
): ExerciseDataSource {

    private val exercises = db.exercises

    override suspend fun getExerciseById(exerciseId: Int) : Exercise? {
        exercises.find { it.id eq exerciseId }?.let {
            return exerciseEntityToExercise(it)
        }
        return null
    }

    override suspend fun getAllLordosisByScoreDesc() : List<ExerciseEntity> {
       return exercises.sortedByDescending { it.lordosisScore }.toList()
    }

    override suspend fun getAllRoundedShouldersByScoreDesc() : List<Exercise> {
        return exercises.sortedByDescending { it.roundedShScore }
            .map {
                exerciseEntityToExercise(it)
            }
            .toList()
    }

    override suspend fun getAllHeadForwardByScoreDesc() : List<Exercise> {
        return exercises.sortedByDescending { it.headFwdScore }
            .map {
                exerciseEntityToExercise(it)
            }
            .toList()
    }
}