package com.chathurangashan.mvvmerrorhandling.viewmodel

import androidx.lifecycle.*
import com.chathurangashan.mvvmerrorhandling.data.enums.ProcessingStatus
import com.chathurangashan.mvvmerrorhandling.data.general.PlantDetails
import com.chathurangashan.mvvmerrorhandling.repositories.PlantDetailsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

class PlantDetailsViewModel @AssistedInject constructor(
    val repository: PlantDetailsRepository,
    @Assisted private val plantId: Int) : BaseViewModel(repository) {

    val plantsDetailLiveData: LiveData<PlantDetails>

    @AssistedFactory
    interface PlantDetailsViewModelFactory{
        fun create(plantId: Int): PlantDetailsViewModel
    }

    init{

        plantsDetailLiveData = repository.plantDetailLiveData.map{
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

    companion object {

        @Suppress("UNCHECKED_CAST")
        fun providesFactory(
            assistedFactory: PlantDetailsViewModelFactory,
            plantId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(plantId) as T
            }
        }
    }
}