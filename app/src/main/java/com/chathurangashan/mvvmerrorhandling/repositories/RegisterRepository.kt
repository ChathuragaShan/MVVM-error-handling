package com.chathurangashan.mvvmerrorhandling.repositories

import androidx.lifecycle.MutableLiveData
import com.chathurangashan.mvvmerrorhandling.R
import com.chathurangashan.mvvmerrorhandling.data.moshi.RegisterRequest
import com.chathurangashan.mvvmerrorhandling.network.ApiService
import com.chathurangashan.mvvmerrorhandling.viewmodel.RegisterViewModel
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RegisterRepository @Inject constructor(private val apiService: ApiService) :
    BaseRepository() {

    val registerLiveData = MutableLiveData<String>()

    suspend fun registerUser(requestBody: RegisterRequest) {

        try {
            val response = apiService.registerUsers(requestBody)

            if (response.status) {
                registerLiveData.value = response.message
            } else {

                val errors = mutableMapOf<String, String>()
                response.errors?.forEach { error ->
                    when (error.fieldKey) {
                        "user_name" -> {
                            errors[RegisterViewModel.usernameErrorKey] = error.errorMessage
                        }
                        "email" -> {
                            errors[RegisterViewModel.emailErrorKey] = error.errorMessage
                        }
                    }
                }

                responseError(response.message, errors)
            }

        } catch (throwable: Throwable) {

            when (throwable) {
                is IOException -> processingError()
                is HttpException -> {
                    connectionError(throwable.message())
                }
                else -> {
                    processingError()
                }
            }

        }

    }
}