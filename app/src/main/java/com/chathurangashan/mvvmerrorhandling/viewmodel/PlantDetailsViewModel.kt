package com.chathurangashan.mvvmerrorhandling.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.chathurangashan.mvvmerrorhandling.data.enums.ProcessingStatus
import com.chathurangashan.mvvmerrorhandling.data.general.PlantDetails
import com.chathurangashan.mvvmerrorhandling.repositories.PlantDetailsRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch

class PlantDetailsViewModel @AssistedInject constructor(
    val repository: PlantDetailsRepository,
    @Assisted private val plantId: Int) : BaseViewModel(repository) {

    val plantsDetailLiveData: LiveData<PlantDetails>

    @AssistedInject.Factory
    interface Factory{
        fun create(plantId: Int): PlantDetailsViewModel
    }

    init{

        plantsDetailLiveData = Transformations.map(repository.plantDetailLiveData){
            isProcessing.value = ProcessingStatus.COMPLETED
            return@map it
        }

        getPlantDetail(plantId)

    }

    private fun getPlantDetail(plantId: Int){

        viewModelScope.launch {

            isProcessing.value = ProcessingStatus.PROCESSING
            repository.getPlantDetails(plantId)

        }
    }
}