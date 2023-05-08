package com.licenta.data.datasources

import com.licenta.data.db.captures
import com.licenta.data.db.mappers.captureEntityToCaptureRes
import com.licenta.data.db.mappers.captureReqToCaptureEntity
import com.licenta.data.db.users
import com.licenta.data.models.request.CaptureReq
import com.licenta.data.models.response.CaptureRes
import org.ktorm.database.Database
import org.ktorm.dsl.and
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.filter
import org.ktorm.entity.find
import org.ktorm.entity.map
import java.time.LocalDate

class CaptureDataSourceImpl(
    private val db: Database
) : CaptureDataSource {

    private val captures = db.captures
    override suspend fun getAllCaptures(id: Int) : List<CaptureRes> {
        return captures.filter { it.userId eq id }
            .map { row -> captureEntityToCaptureRes(row) }
    }

    override suspend fun getCaptureByDate(date: LocalDate) : CaptureRes? {
        return captures.find { it.date eq date }?.let {
            captureEntityToCaptureRes(it)
         }
    }

    override suspend fun insertCapture(id: Int, newCapture: CaptureReq) : Boolean {
        try {
            val user = db.users.find { it.id eq id }
            if(user == null) return false

            val capture = captureReqToCaptureEntity(newCapture).also {
                it.user = user
                it.date = LocalDate.now()
            }
            captures.add(capture)
            println(capture.id)
        }
        catch(e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    override fun deleteCaptureByDate(userId: Int, date: LocalDate) {
        captures.find { it.userId eq userId and(it.date eq date) }
    }
}