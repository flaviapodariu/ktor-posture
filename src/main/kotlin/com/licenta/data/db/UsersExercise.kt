package com.licenta.data.db

import kotlinx.serialization.Serializable
import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.int

@Serializable
sealed interface UsersExercise : Entity<UsersExercise> {
    @Serializable
    companion object : Entity.Factory<UsersExercise>()
    var user: User
    var exercise: ExerciseEntity
    var reps: Int
}

object UsersExercises : Table<UsersExercise>(tableName = "users_exercises") {
    val userId = int("userId").primaryKey().references(Users) { it.user }
    val exerciseId = int("exerciseId").primaryKey().references(ExercisesEntitity) { it.exercise }
    val reps = int("reps").bindTo { it.reps }
}

val Database.userExercises get() = this.sequenceOf(UsersExercises)