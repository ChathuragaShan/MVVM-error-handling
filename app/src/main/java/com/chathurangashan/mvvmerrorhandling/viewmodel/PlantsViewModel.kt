package com.chathurangashan.mvvmerrorhandling.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.chathurangashan.mvvmerrorhandling.data.enums.ProcessingStatus
import com.chathurangashan.mvvmerrorhandling.data.general.Plant
import com.chathurangashan.mvvmerrorhandling.repositories.PlantsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantsViewModel @Inject constructor(val repository: PlantsRepository): BaseViewModel(repository) {

    val plantsLiveData: LiveData<List<Plant>>

    init {

        plantsLiveData = Transformations.map(repository.plantsLiveData){
            isProcessing.value = ProcessingStatus.COMPLETED
            return@map it
        }

        getPlants()

    }

    private fun getPlants(){

        viewModelScope.launch {

            isProcessing.value = ProcessingStatus.PROCESSING
            repository.getPlants()

        }
    }
}