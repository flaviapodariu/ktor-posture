package com.licenta.data.db

import kotlinx.serialization.Serializable
import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

@Serializable
sealed interface ExerciseMuscle : Entity<ExerciseMuscle> {
    @Serializable
    companion object : Entity.Factory<ExerciseMuscle>()
    var exercise: ExerciseEntity
    var muscle: MuscleEntity
    var type: String
}

object ExercisesMuscles : Table<ExerciseMuscle>(tableName = "exercise_muscle") {
    val exerciseId = int("exerciseId").primaryKey().references(ExercisesEntitity) { it.exercise }
    val muscleId = int("muscleId").primaryKey().references(MusclesEntity) {it.muscle}
    val type = varchar("type").bindTo { it.type }
}

val Database.exercisesMuscles get( ) = this.sequenceOf(ExercisesMuscles)