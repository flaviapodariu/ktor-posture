package com.licenta.data.models.response

import kotlinx.serialization.Serializable

@Serializable
data class PostureHistory(
    val captures: List<CaptureRes> = listOf()
)



