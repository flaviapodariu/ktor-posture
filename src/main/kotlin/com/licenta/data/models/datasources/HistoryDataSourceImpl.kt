package com.licenta.data.models.datasources

import com.licenta.data.models.db.Capture
import com.licenta.data.models.db.Captures
import com.licenta.data.models.db.captures
import com.licenta.data.models.db.mappers.captureToCaptureDto
import com.licenta.data.models.db.mappers.postureCaptureReqToCapture
import com.licenta.data.models.db.users
import com.licenta.data.models.request.PostureCaptureReq
import com.licenta.data.models.response.CaptureDto
import com.licenta.data.models.response.PostureHistory
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.*
import java.time.LocalDate

class HistoryDataSourceImpl(
    val db: Database
) : HistoryDataSource {

    private val captures = db.captures
    override suspend fun getHistory(id: Int): List<CaptureDto> {
        return captures.filter { it.userId eq id }
            .map { row -> captureToCaptureDto(row) }
    }

    override suspend fun getCaptureByDate(date: LocalDate): CaptureDto? {
        return captures.find { it.date eq date }?.let {
            captureToCaptureDto(it)
        }
    }

    override suspend fun addNewCapture(id: Int, newCapture: PostureCaptureReq): Boolean {

        val user = db.users.find { it.id eq id }!!
        val capture = postureCaptureReqToCapture(newCapture).also {
            it.user = user
            it.date = LocalDate.now()
        }
        captures.add(capture)
        println(capture.id)
        return true

    }

}