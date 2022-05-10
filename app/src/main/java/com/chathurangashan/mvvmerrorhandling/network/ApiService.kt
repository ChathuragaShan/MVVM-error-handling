package com.chathurangashan.mvvmerrorhandling.network

import com.chathurangashan.mvvmerrorhandling.data.moshi.requests.LoginRequest
import com.chathurangashan.mvvmerrorhandling.data.moshi.requests.RegisterRequest
import com.chathurangashan.mvvmerrorhandling.data.moshi.response.login.LoginResponse
import com.chathurangashan.mvvmerrorhandling.data.moshi.response.register.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("users/register")
    suspend fun registerUsers(@Body registerRequest: RegisterRequest): RegisterResponse

    @POST("users/login")
    suspend fun userLogin(@Body loginRequest: LoginRequest): LoginResponse
}