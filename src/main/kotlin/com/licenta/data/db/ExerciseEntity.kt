package com.licenta.data.db

import kotlinx.serialization.Serializable
import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

@Serializable
sealed interface ExerciseEntity : Entity<ExerciseEntity> {
    @Serializable
    companion object : Entity.Factory<ExerciseEntity>()

    val id: Int
    var name: String
    var description: String
    var imageUrl: String

}

object ExercisesEntitity : Table<ExerciseEntity>(tableName = "exercises") {
    val id = int("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
    val description = varchar("description").bindTo { it.description }
    val imageUrl =  varchar("image_url").bindTo { it.imageUrl }
}

val Database.exercises get() = this.sequenceOf(ExercisesEntitity)