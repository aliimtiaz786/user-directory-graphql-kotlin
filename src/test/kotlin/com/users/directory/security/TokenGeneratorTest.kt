package com.users.directory.security

import com.auth0.jwt.exceptions.JWTDecodeException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TokenGeneratorTest {

    private final val tokenGenerator = TokenGenerator()

    @Test
    public fun shouldGenerateToken() {

        val userName = "test-username"
        val password = "test-password"

        val jwtToken = tokenGenerator.createToken(userName, password);
        val decodedJWT = tokenGenerator.decode(jwtToken)
        val claims = decodedJWT.claims
        val actualUserName: String? = claims.get("username")?.asString();
        val actualPassword: String? = claims.get("password")?.asString();
        assertThat(actualUserName).isEqualTo(userName)
        assertThat(actualPassword).isEqualTo(password)
    }


    @Test
    public fun shouldNotDecodeTokenIfInvalidTokenSent() {

        val userName = "test-username"
        val password = "test-password"

        val jwtToken = tokenGenerator.createToken(userName, password);
        assertThatThrownBy { tokenGenerator.decode("invalid-token") }
            .isExactlyInstanceOf(JWTDecodeException::class.java)
            .hasMessage(
                "The token was expected to have 3 parts, but got 1."
            )
    }
}