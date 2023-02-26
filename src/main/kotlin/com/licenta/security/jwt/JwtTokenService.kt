package com.licenta.security.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

class JwtTokenService {
    fun generate(
        config: TokenConfig,
        vararg claims: TokenClaim
    ): String {
        var token = JWT.create()
            .withAudience(config.audience)
            .withIssuer(config.issuer)
            .withExpiresAt(Date(System.currentTimeMillis() + config.expiresIn))
        claims.forEach {
            token.withClaim(it.claim, it.value)
        }

        return token.sign(Algorithm.HMAC256(config.secret))
    }
}