package com.chathurangashan.mvvmerrorhandling.network

import com.chathurangashan.mvvmerrorhandling.data.moshi.requests.LoginRequest
import com.chathurangashan.mvvmerrorhandling.data.moshi.requests.RegisterRequest
import com.chathurangashan.mvvmerrorhandling.data.moshi.response.login.LoginResponse
import com.chathurangashan.mvvmerrorhandling.data.moshi.response.plant.PlantResponse
import com.chathurangashan.mvvmerrorhandling.data.moshi.response.plantdetails.PlantDetailResponse
import com.chathurangashan.mvvmerrorhandling.data.moshi.response.register.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("users/register")
    suspend fun registerUsers(@Body registerRequest: RegisterRequest): RegisterResponse

    @POST("users/login")
    suspend fun userLogin(@Body loginRequest: LoginRequest): LoginResponse

    @GET("items/plants")
    suspend fun getPlants(): PlantResponse

    @GET("items/plantDetails/{plantId}")
    suspend fun  getPlantDetails(@Path("plantId") plantId: Int): PlantDetailResponse

}