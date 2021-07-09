package com.users.directory.schema

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
@AutoConfigureWebTestClient
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class UserMutationTest(@Autowired private val testClient: WebTestClient) {

    @Test
    fun userMutationShouldReturnAuthToken() {

        val query = "getUserAuthToken"
        val userName = "Patton Down DeHatches"
        val password = "DmFPPuM9"

        testClient.post()
            .uri(GRAPHQL_ENDPOINT)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(GRAPHQL_MEDIA_TYPE)
            .bodyValue("mutation { $query(userName: \"$userName\", password: \"$password\") }")
            .exchange()
            .verifyOnlyDataExists(query)
            .jsonPath("$DATA_JSON_PATH.$query").isNotEmpty


    }


    @Test
    fun userMutationShouldThrowErrorIfUserDoesNotExists() {

        val query = "getUserAuthToken"
        val userName = "Patton Down DeHatches111"
        val password = "DmFPPuM9"

        testClient.post()
            .uri(GRAPHQL_ENDPOINT)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(GRAPHQL_MEDIA_TYPE)
            .bodyValue("mutation { $query(userName: \"$userName\", password: \"$password\") }")
            .exchange()
            .verifyError("Exception while fetching data (/getUserAuthToken) : User with username [Patton Down DeHatches111] and password [DmFPPuM9] not found")


    }
}