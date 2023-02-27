package com.licenta.security

import com.licenta.data.models.BackProblem
import org.apache.commons.codec.digest.DigestUtils
import java.security.SecureRandom

class HashingServiceImpl: HashingService {
    override fun generateSaltedHash(plainText: String, saltLength: Int) : SaltedHash {
        val salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLength)
        val hash = DigestUtils.sha256Hex("$salt$plainText")
        return SaltedHash(
            salt = salt.toString(),
            hash= hash
        )
    }
    override fun verify(plainText: String, saltedHash: SaltedHash) : Boolean {
        return DigestUtils.sha256Hex(saltedHash.salt + plainText) == saltedHash.hash
    }
}