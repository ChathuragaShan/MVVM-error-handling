package com.chathurangashan.mvvmerrorhandling.data.moshi.response.plant

import com.chathurangashan.mvvmerrorhandling.data.moshi.InputError
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlantResponse(
    @Json(name = "status")
    val status: Boolean,
    @Json(name = "message")
    val message: String,
    @Json(name = "data")
    val `data`: List<Data>?,
    @Json(name = "errors")
    val errors: List<InputError>?
)