package com.licenta.data.models.request

import kotlinx.serialization.Serializable

@Serializable
data class PostureCaptureReq(
    val headForward: Float,
    val lordosis: Float,
    val roundedShoulders: Float
)