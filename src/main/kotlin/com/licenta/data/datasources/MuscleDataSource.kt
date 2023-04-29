package com.licenta.data.datasources

import com.licenta.data.db.MuscleEntity

interface MuscleDataSource {
    suspend fun getMuscleName(id: Int) : MuscleEntity?
}