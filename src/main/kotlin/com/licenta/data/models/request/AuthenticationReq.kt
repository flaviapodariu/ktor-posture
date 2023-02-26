package com.licenta.data.models.request

import kotlinx.serialization.Serializable

@Serializable
data class RegisterReq(
    val email: String,
    val nickname: String,
    val password: String
)

@Serializable
data class LoginReq(
    val email: String,
    val password: String
)
