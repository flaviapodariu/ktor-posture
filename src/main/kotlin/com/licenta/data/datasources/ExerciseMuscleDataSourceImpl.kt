package com.licenta.data.datasources

import com.licenta.data.db.ExerciseEntity
import com.licenta.data.db.exercisesMuscles
import com.licenta.data.db.mappers.exerciseEntityToExercise
import com.licenta.data.db.mappers.exerciseMuscleToDtoAddOn
import com.licenta.data.db.mappers.exerciseToExerciseEntity
import com.licenta.data.db.muscles
import com.licenta.data.models.Exercise
import com.licenta.data.models.response.ExerciseMuscleType
import org.ktorm.database.Database
import org.ktorm.dsl.and
import org.ktorm.dsl.eq
import org.ktorm.entity.filter
import org.ktorm.entity.find
import org.ktorm.entity.map
import org.ktorm.entity.toList

class ExerciseMuscleDataSourceImpl(
    db: Database
) : ExerciseMuscleDataSource{

    private val joinTable = db.exercisesMuscles
    private val muscles = db.muscles

    override suspend fun getAllMuscleTargetsByExercise(exerciseId: Int): List<ExerciseMuscleType> {
        return joinTable.filter { it.exerciseId eq exerciseId }.map{
            exerciseMuscleToDtoAddOn(it)
        }
    }

    override suspend fun getExercisesByMuscleNameAndType(muscleName: String, type: String) : List<ExerciseEntity> {
        val muscleId = muscles.find { it.name eq muscleName }?.id ?: return listOf()
        return joinTable.filter { it.muscleId eq muscleId and(it.type eq type) }.map{
            it.exercise
        }
    }
}