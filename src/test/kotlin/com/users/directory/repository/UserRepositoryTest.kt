package com.users.directory.repository

import com.users.directory.exception.UserNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class UserRepositoryTest(@Autowired val userRepository: UserRepository) {


    @Test
    fun shouldParseFileAndStoreUsers() {
        val users = userRepository.getUsers();
        assertThat(users).isNotNull().isNotEmpty().hasSize(11)
        val firstUser = users.get(0)
        assertThat(firstUser.firstName).isEqualTo("Parker")
        assertThat(firstUser.lastName).isEqualTo("Lubowitz")
        assertThat(firstUser.userName).isEqualTo("Patton Down DeHatches")
        assertThat(firstUser.password).isEqualTo("DmFPPuM9")
        assertThat(firstUser.email).isEqualTo("lanette.cronin.dvm@yahoo.com")
    }


    @Test
    fun shouldReturnUserAuthTokenBasedOnUserNameAndPassword() {
        val authToken = userRepository.getUserAuthToken("Patton Down DeHatches", "DmFPPuM9");
        assertThat(authToken).isNotNull().isNotEmpty();
    }


    @Test
    fun shouldThrowExceptionIfUserNotFoundBasedOnUserNamePassword() {
        assertThatThrownBy { userRepository.getUserAuthToken("test", "test") }
            .isExactlyInstanceOf(UserNotFoundException::class.java)
            .hasMessage("User with username [test] and password [test] not found")
    }


}