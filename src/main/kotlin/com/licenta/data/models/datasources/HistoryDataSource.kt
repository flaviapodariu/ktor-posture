package com.licenta.data.models.datasources

import com.licenta.data.models.db.Capture
import com.licenta.data.models.response.PostureHistory
import com.licenta.data.models.request.PostureCaptureReq
import com.licenta.data.models.response.CaptureDto
import java.time.LocalDate

interface HistoryDataSource {

    suspend fun getHistory(id: Int) : List<CaptureDto>

    suspend fun getCaptureByDate(date: LocalDate) : CaptureDto?

    suspend fun addNewCapture(id: Int, newCapture: PostureCaptureReq) : Boolean
}