package com.users.directory.schema

import org.hamcrest.Matchers
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
internal class UserQueryTest(@Autowired private val testClient: WebTestClient) {


    @Test
    fun shouldQueryUsers() {
        val query = "users"

        testClient.post()
            .uri(GRAPHQL_ENDPOINT)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(GRAPHQL_MEDIA_TYPE)
            .bodyValue("query { $query { firstName, lastName, userName, password, email } }")
            .exchange()
            .verifyOnlyDataExists(query)
            .jsonPath("$DATA_JSON_PATH.$query").value(Matchers.hasSize<Int>(11))
            .jsonPath("$DATA_JSON_PATH.$query[0].firstName").isEqualTo("Parker")
            .jsonPath("$DATA_JSON_PATH.$query[0].lastName").isEqualTo("Lubowitz")
            .jsonPath("$DATA_JSON_PATH.$query[0].userName").isEqualTo("Patton Down DeHatches")
            .jsonPath("$DATA_JSON_PATH.$query[0].password").isEqualTo("DmFPPuM9")
            .jsonPath("$DATA_JSON_PATH.$query[0].email").isEqualTo("lanette.cronin.dvm@yahoo.com")
    }


    @Test
    fun shouldThrowErrorOnWrongQuery() {
        val query = "users"

        testClient.post()
            .uri(GRAPHQL_ENDPOINT)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(GRAPHQL_MEDIA_TYPE)
            .bodyValue("query { $query { firstName111, lastName } }")
            .exchange()
            .verifyError("Validation error of type FieldUndefined: Field 'firstName111' in type 'User' is undefined @ 'users/firstName111'")
    }

}