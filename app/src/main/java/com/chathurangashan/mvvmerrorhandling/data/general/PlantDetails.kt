package com.chathurangashan.mvvmerrorhandling.data.general

data class PlantDetails(
    val id: Int,
    val name: String,
    val image: Int,
    val price: Double,
    val sizes: List<String>,
    val planters: List<String>,
    val description: Int
)