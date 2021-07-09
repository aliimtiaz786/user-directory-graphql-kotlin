package com.users.directory.repository

import com.fasterxml.jackson.module.kotlin.readValue
import com.users.directory.exception.UserNotFoundException
import com.users.directory.schema.User
import com.users.directory.security.TokenGenerator
import com.users.directory.utils.JSONHelper
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Repository
import javax.annotation.PostConstruct


@Repository
class UserRepository(
    @Value("classpath:data/users.json") val resource: Resource,
    val tokenGenerator: TokenGenerator
) {


    private val users: MutableList<User> = mutableListOf()

    @PostConstruct
    fun loadUsers() {
        val file = resource.file
        JSONHelper.getObjectMapper().readValue<List<User>>(file).forEach { p -> users.add(p) };
    }

    fun getUsers(): List<User> {
        return users
    }

    fun getUserAuthToken(userName: String, password: String): String {
        val user = users.stream()
            .filter { user -> user.userName.equals(userName) && user.password.equals(password) }
            .findFirst().orElseThrow {
                UserNotFoundException(
                    String.format(
                        "User with username [%s] and password [%s] not found",
                        userName,
                        password
                    )
                )
            };

        return tokenGenerator.createToken(userName, password)
    }

}