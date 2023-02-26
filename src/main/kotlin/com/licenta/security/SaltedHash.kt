package com.licenta.security

data class SaltedHash(
    val salt: String,
    val hash: String
)
