package com.ravikumar.testapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ravikumar.testapp.helperClasses.Resource
import com.ravikumar.testapp.models.Product
import com.ravikumar.testapp.repositories.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) :
    ViewModel() {

    private val _onAllProductsResponseLiveData = MutableLiveData<Resource<List<Product>>>()

    fun getOnAllProductsLiveData(): LiveData<Resource<List<Product>>> {
        return _onAllProductsResponseLiveData
    }

    fun getAllProducts() {
        _onAllProductsResponseLiveData.value = Resource.Loading()
        this.viewModelScope.launch {
            _onAllProductsResponseLiveData.value = repository.getAllProducts()
        }
    }

    fun addFavorite(product: Product) {
        repository.addFavorite(product)
    }

    fun removeFavorite(product: Product) {
        repository.removeFavorite(product)
    }

    fun isAlreadyAdded(id: Int, callback: (Boolean) -> Unit) {
        repository.isAlreadyAdded(id, callback)
    }
}