package com.chathurangashan.mvvmerrorhandling.data.moshi.response.login

import com.chathurangashan.mvvmerrorhandling.data.moshi.response.login.Data
import com.chathurangashan.mvvmerrorhandling.data.moshi.InputError
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse(
    @Json(name = "status")
    val status: Boolean,
    @Json(name = "message")
    val message: String,
    @Json(name = "data")
    val `data`: Data?,
    @Json(name = "errors")
    val errors: List<InputError>?
)