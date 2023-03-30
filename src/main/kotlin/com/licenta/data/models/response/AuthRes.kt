package com.licenta.data.models.response

import kotlinx.serialization.Serializable

@Serializable
data class AuthRes(
    val token: String,
    val nickname: String
)