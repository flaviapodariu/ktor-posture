package com.licenta.data.datasources

import com.licenta.data.db.exercisesMuscles
import com.licenta.data.db.mappers.exerciseEntityToExercise
import com.licenta.data.models.Exercise
import org.ktorm.database.Database
import org.ktorm.dsl.and
import org.ktorm.dsl.eq
import org.ktorm.entity.filter
import org.ktorm.entity.map

class ExerciseMuscleDataSourceImpl(
    db: Database
) : ExerciseMuscleDataSource{

    private val joinTable = db.exercisesMuscles

    override suspend fun getExercisesByMuscleAndType(muscleId: Int, type: String) : List<Exercise> {
        return joinTable.filter { it.muscleId eq muscleId and(it.type eq type)  }
            .map { exerciseEntityToExercise(it.exercise) }
    }
}