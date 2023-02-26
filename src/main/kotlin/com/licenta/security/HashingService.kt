package com.licenta.security

interface HashingService {

    fun generateSaltedHash(plainText: String, saltLength: Int = 16) : SaltedHash

    fun verify(plainText: String, saltedHash: SaltedHash) : Boolean
}