package com.chathurangashan.mvvmerrorhandling.repositories

import androidx.lifecycle.MutableLiveData
import com.chathurangashan.mvvmerrorhandling.data.general.PlantDetails
import com.chathurangashan.mvvmerrorhandling.network.ApiService
import com.chathurangashan.mvvmerrorhandling.network.ConnectivityInterceptor
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class PlantDetailsRepository @Inject constructor(private val apiService: ApiService)
    : BaseRepository() {

    val plantDetailLiveData = MutableLiveData<PlantDetails>()

    suspend fun getPlantDetails(plantId: Int) {

        try {

            val response = apiService.getPlantDetails(plantId)

            if (response.status) {

                val (id, name, image, price, sizes, planters, description) = response.data
                plantDetailLiveData.value =
                    PlantDetails(id, name, image, price, sizes, planters, description)

            } else {

                responseError(response.message)

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