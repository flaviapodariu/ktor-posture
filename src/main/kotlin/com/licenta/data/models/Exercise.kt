package com.licenta.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Exercise(
    val id: Int,
    var name: String,
    var description: String,
    var lordosisScore: Int,
    var headFwdScore: Int,
    var roundedShScore: Int
)
