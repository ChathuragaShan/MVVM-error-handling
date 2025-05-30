package com.chathurangashan.mvvmerrorhandling.repositories

import androidx.lifecycle.MutableLiveData
import com.chathurangashan.mvvmerrorhandling.data.moshi.requests.RegisterRequest
import com.chathurangashan.mvvmerrorhandling.network.ApiService
import com.chathurangashan.mvvmerrorhandling.utilities.SingleLiveEvent
import com.chathurangashan.mvvmerrorhandling.viewmodel.RegisterViewModel
import com.chathurangashan.mvvmerrorhandling.network.ConnectivityInterceptor
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class RegisterRepository @Inject constructor(private val apiService: ApiService) :
    BaseRepository() {

    val registerLiveData = MutableLiveData<SingleLiveEvent<String>>()

    suspend fun registerUser(requestBody: RegisterRequest) {

        processApiResult {
            val response = apiService.registerUsers(requestBody)

            if (response.status) {
                registerLiveData.value = SingleLiveEvent(response.message)
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

        }
    }
}