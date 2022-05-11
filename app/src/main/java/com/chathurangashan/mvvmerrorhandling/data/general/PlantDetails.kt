package com.chathurangashan.mvvmerrorhandling.data.general

data class PlantDetails(
    val name: String,
    val image: Int,
    val price: Int,
    val sizes: List<String>,
    val planter: List<String>,
    val colors: List<Int>,
    val description: String
)