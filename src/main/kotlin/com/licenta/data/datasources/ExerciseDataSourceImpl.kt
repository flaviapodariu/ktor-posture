package com.licenta.data.datasources

import com.licenta.data.db.ExerciseEntity
import com.licenta.data.db.exercises
import com.licenta.data.db.mappers.exerciseEntityToExercise
import com.licenta.data.models.Exercise
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.greaterEq
import org.ktorm.entity.filter
import org.ktorm.entity.find
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


}