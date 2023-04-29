package com.licenta.data.db

import kotlinx.serialization.Serializable
import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.int

@Serializable
sealed interface UserExercise : Entity<UserExercise> {
    @Serializable
    companion object : Entity.Factory<UserExercise>()
    var user: User
    var exercise: ExerciseEntity
    var reps: Int
}

object UsersExercises : Table<UserExercise>(tableName = "user_exercise") {
    val userId = int("userId").primaryKey().references(Users) { it.user }
    val exerciseId = int("exerciseId").primaryKey().references(ExercisesEntitity) { it.exercise }
    val reps = int("reps").bindTo { it.reps }
}

val Database.userExercises get() = this.sequenceOf(UsersExercises)