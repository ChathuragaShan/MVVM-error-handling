package com.chathurangashan.mvvmerrorhandling.network

import com.chathurangashan.mvvmerrorhandling.data.moshi.RegisterRequest
import com.chathurangashan.mvvmerrorhandling.data.moshi.register_response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("users/register")
    suspend fun registerUsers(@Body registerRequest: RegisterRequest): RegisterResponse
}