package com.chathurangashan.mvvmerrorhandling.data.moshi.response.login


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "user_name")
    val userName: String
)