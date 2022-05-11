package com.chathurangashan.mvvmerrorhandling.repositories

import androidx.lifecycle.MutableLiveData
import com.chathurangashan.mvvmerrorhandling.data.general.Plant
import com.chathurangashan.mvvmerrorhandling.network.ApiService
import com.chathurangashan.mvvmerrorhandling.network.ConnectivityInterceptor
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class PlantsRepository @Inject constructor(private val apiService: ApiService) :
    BaseRepository()  {

    val plantsLiveData = MutableLiveData<List<Plant>>()

    suspend fun getPlants() {

        try {

            val response = apiService.getPlants()

            if (response.status) {

                val plantList = mutableListOf<Plant>()

                response.data?.forEach {
                    val plant = Plant(it.id, it.name, it.image, it.price)
                    plantList.add(plant)
                }

                plantsLiveData.value = plantList

            } else {

                responseError(response.message)
            }

        }catch (exception: Exception){

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