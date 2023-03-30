package com.licenta.data.models.response

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class CaptureRes(
    val id: Int,
    var userId: Int,
    var headForward: Float,
    var lordosis: Float,
    var roundedShoulders: Float,
    @Contextual
    var date: LocalDate

)
