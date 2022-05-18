package com.chathurangashan.mvvmerrorhandling.data.moshi.response.plantdetails

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "image")
    val image: Int,
    @Json(name = "price")
    val price: Double,
    @Json(name = "sizes")
    val sizes: List<String>,
    @Json(name = "planters")
    val planters: List<String>,
    @Json(name = "description")
    val description: Int,
)
