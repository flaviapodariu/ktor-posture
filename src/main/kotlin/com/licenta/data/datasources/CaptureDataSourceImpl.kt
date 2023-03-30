package com.licenta.data.datasources

import com.licenta.data.db.captures
import com.licenta.data.db.mappers.captureToCaptureDto
import com.licenta.data.db.mappers.postureCaptureReqToCapture
import com.licenta.data.db.users
import com.licenta.data.models.request.CaptureReq
import com.licenta.data.models.response.CaptureRes
import com.licenta.data.models.response.PostureHistory
import org.ktorm.database.Database
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
    override suspend fun getAllCaptures(id: Int) : PostureHistory {
        val capturesList =  captures.filter { it.userId eq id }
            .map { row -> captureToCaptureDto(row) }

        return PostureHistory(capturesList)
    }

    override suspend fun getCaptureByDate(date: LocalDate) : CaptureRes? {
        return captures.find { it.date eq date }?.let {
            captureToCaptureDto(it)
         }
    }

    override suspend fun insertCapture(id: Int, newCapture: CaptureReq) : Boolean {
        try {
            val user = db.users.find { it.id eq id }!!
            val capture = postureCaptureReqToCapture(newCapture).also {
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

}