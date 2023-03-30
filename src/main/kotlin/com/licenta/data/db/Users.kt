package com.licenta.data.db

import kotlinx.serialization.Serializable
import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

@Serializable

sealed interface User : Entity<User> {
    @Serializable
    companion object : Entity.Factory<User>()
    val id: Int
    var email: String
    var nickname: String
    var password: String
    var salt: String
}

object Users : Table<User>("users") {
    val id = int("id").primaryKey().bindTo { it.id }
    val email = varchar("email").bindTo { it.email }
    val nickname = varchar("nickname").bindTo { it.nickname }
    val password = varchar("password").bindTo { it.password }
    val salt = varchar("salt").bindTo { it.salt }
//    val workoutId = int("workoutId").references(Workout).bindTo { it.workout }
}

val Database.users get() = this.sequenceOf(Users)
