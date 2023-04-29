package com.licenta.data.datasources

import com.licenta.data.db.MuscleEntity
import com.licenta.data.db.muscles
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.find

class MuscleDataSourceImpl(
    db: Database
) : MuscleDataSource {

    private val muscles = db.muscles
    override suspend fun getMuscleName(id: Int): MuscleEntity? {
        return muscles.find { it.id eq id}
    }
}