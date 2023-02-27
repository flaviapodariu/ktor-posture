package com.licenta.data.models.request

import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import java.time.LocalDateTime
import java.util.Date

@Serializable
data class PostureCapture(
    val problemFlag: Int,
//    val currentDate: LocalDateTime
)
