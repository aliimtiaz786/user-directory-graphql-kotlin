package com.users.directory.schema

import com.expediagroup.graphql.server.operations.Mutation
import com.expediagroup.graphql.server.operations.Query
import com.users.directory.repository.UserRepository
import org.springframework.stereotype.Component


@Component
class UserQuery(private val usersRepository: UserRepository) : Query {

    fun users(): List<User> {
        return usersRepository.getUsers()
    }
}

@Component
class UserMutation(private val usersRepository: UserRepository) : Mutation {
    fun getUserAuthToken(userName: String, password: String): String {
        return usersRepository.getUserAuthToken(userName, password)
    }
}

data class User(val firstName: String, val lastName: String, val userName: String, val password: String, val email: String)