package com.users.directory.utils

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule

class JSONHelper {

    companion object {
        fun getObjectMapper(): ObjectMapper {
            return ObjectMapper()
                    .registerModule(KotlinModule())
                    .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                    .enable(JsonGenerator.Feature.IGNORE_UNKNOWN)
                    .enable(JsonParser.Feature.IGNORE_UNDEFINED)
                    .disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES)
                    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        }
    }
}