package com.licenta.data.models.response

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticationRes(
    val token: String,
    val nickname: String
)