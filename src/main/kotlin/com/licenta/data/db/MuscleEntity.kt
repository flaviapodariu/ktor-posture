package com.licenta.data.db

import com.licenta.data.db.ExercisesEntitity.primaryKey
import kotlinx.serialization.Serializable
import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

@Serializable
sealed interface MuscleEntity : Entity<MuscleEntity> {

    @Serializable
    companion object : Entity.Factory<MuscleEntity>()
    val id: Int
    var name: String
}

object MusclesEntity : Table<MuscleEntity>(tableName = "muscles") {
    val id = int("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
}

val Database.muscles get() = this.sequenceOf(MusclesEntity)