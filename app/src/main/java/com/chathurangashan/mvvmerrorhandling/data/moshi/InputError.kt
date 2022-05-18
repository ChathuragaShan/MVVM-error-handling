package com.chathurangashan.mvvmerrorhandling.data.moshi

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InputError(
    @Json(name = "field_key")
    val fieldKey: String,
    @Json(name = "error_message")
    val errorMessage: String
)