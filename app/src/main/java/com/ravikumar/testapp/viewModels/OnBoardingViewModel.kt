package com.ravikumar.testapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ravikumar.testapp.helperClasses.Resource
import com.ravikumar.testapp.models.OnBoardingPageModel
import com.ravikumar.testapp.repositories.OnBoardingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(private val repository: OnBoardingRepository) :
    ViewModel() {

    private val onServerResponseLiveData = MutableLiveData<Resource<List<OnBoardingPageModel>>>()

    fun getOnServerResponseLiveData(): LiveData<Resource<List<OnBoardingPageModel>>> {
        return onServerResponseLiveData
    }

    fun getOnBoardingData() {
        onServerResponseLiveData.value = Resource.Loading()
        this.viewModelScope.launch {
            onServerResponseLiveData.value = repository.getOnBoardingData()
        }
    }

}