package com.licenta.data.datasources

import com.licenta.data.db.exercisesMuscles
import com.licenta.data.db.mappers.exerciseEntityToExercise
import com.licenta.data.db.mappers.exerciseMuscleToDtoAddOn
import com.licenta.data.models.Exercise
import com.licenta.data.models.response.ExerciseMuscleType
import org.ktorm.database.Database
import org.ktorm.dsl.and
import org.ktorm.dsl.eq
import org.ktorm.entity.filter
import org.ktorm.entity.map

class ExerciseMuscleDataSourceImpl(
    db: Database
) : ExerciseMuscleDataSource{

    private val joinTable = db.exercisesMuscles

    override suspend fun getAllMuscleTargetsByExercise(exerciseId: Int): List<ExerciseMuscleType> {
        return joinTable.filter { it.exerciseId eq exerciseId }.map{
            exerciseMuscleToDtoAddOn(it)
        }
    }

    override suspend fun getExercisesByMuscleAndType(muscleId: Int, type: String) : List<Exercise> {
        return joinTable.filter { it.muscleId eq muscleId and(it.type eq type)  }
            .map { exerciseEntityToExercise(it.exercise) }
    }
}