package com.chathurangashan.mvvmerrorhandling.data.moshi.register_response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterResponse(
        @Json(name = "status")
        val status: Boolean,
        @Json(name = "message")
        val message: String,
        @Json(name = "errors")
        val errors: List<Error>?
)