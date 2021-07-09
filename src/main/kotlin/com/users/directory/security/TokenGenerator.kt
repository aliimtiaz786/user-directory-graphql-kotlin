package com.users.directory.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

@Service
class TokenGenerator {

    private final val APP_SECRET: String = "test-secret-key";
    private final val PLUS_DAYS_EXPIRY: Long = 5;


    public fun createToken(userName: String, password: String): String {

        val jwt: String = JWT.create()
            .withClaim("iss", "appId")
            .withClaim("sub", "LoginRequest")
            .withClaim("username", userName)
            .withClaim("password", password)
            .withExpiresAt(
                Date.from(
                    LocalDate.now().plusDays(PLUS_DAYS_EXPIRY).atStartOfDay(ZoneId.systemDefault())
                        .toInstant()
                )
            )
            .sign(Algorithm.HMAC256(APP_SECRET));

        return jwt
    }


    public fun decode(token: String): DecodedJWT {
        val decodedJWT = JWT.decode(token);
        return decodedJWT
    }
}