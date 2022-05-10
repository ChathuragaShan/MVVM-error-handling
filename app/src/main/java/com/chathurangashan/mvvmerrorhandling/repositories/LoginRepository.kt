package com.chathurangashan.mvvmerrorhandling.repositories

import androidx.lifecycle.MutableLiveData
import com.chathurangashan.mvvmerrorhandling.data.general.LoginDetails
import com.chathurangashan.mvvmerrorhandling.data.moshi.requests.LoginRequest
import com.chathurangashan.mvvmerrorhandling.network.ApiService
import com.chathurangashan.mvvmerrorhandling.network.ConnectivityInterceptor
import com.chathurangashan.mvvmerrorhandling.utilities.SingleLiveEvent
import com.chathurangashan.mvvmerrorhandling.viewmodel.LoginViewModel
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class LoginRepository @Inject constructor(private val apiService: ApiService): BaseRepository() {

    val loginLiveData = MutableLiveData<SingleLiveEvent<LoginDetails>>()

    suspend fun login(loginRequest: LoginRequest){

        try {

            val response = apiService.userLogin(loginRequest)

            if (response.status) {

                val loginDetails = LoginDetails(response.message, response.data!!.userName)
                loginLiveData.value = SingleLiveEvent(loginDetails)

            }else{

                val errors = mutableMapOf<String, String>()
                response.errors?.forEach { error ->
                    when (error.fieldKey) {
                        "email" -> {
                            errors[LoginViewModel.emailErrorKey] = error.errorMessage
                        }
                    }
                }

                responseError(response.message, errors)
            }

        } catch (exception: Exception) {

            when (exception) {
                is HttpException -> connectionError(exception.message())
                is ConnectivityInterceptor.NoConnectivityException -> noConnectivityError()
                is SocketTimeoutException -> timeoutConnectionError()
                is IOException -> processingError()
                else -> processingError()
            }

        }
    }
}