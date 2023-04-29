package com.licenta.data.datasources

import com.licenta.data.models.request.CaptureReq
import com.licenta.data.models.response.CaptureRes
import java.time.LocalDate

interface CaptureDataSource {
    suspend fun getAllCaptures(id: Int) : List<CaptureRes>

    suspend fun getCaptureByDate(date: LocalDate) : CaptureRes?

    suspend fun insertCapture(id: Int, newCapture: CaptureReq) : Boolean
}