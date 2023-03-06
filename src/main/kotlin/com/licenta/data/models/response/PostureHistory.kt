package com.licenta.data.models.response

import kotlinx.serialization.Serializable

@Serializable
data class PostureHistory(
    val postureCaptures: List<CaptureDto> = listOf()
)



