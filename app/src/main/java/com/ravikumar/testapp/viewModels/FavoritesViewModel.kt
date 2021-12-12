package com.ravikumar.testapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ravikumar.testapp.models.Product
import com.ravikumar.testapp.repositories.FavoritesRepository
import com.ravikumar.testapp.roomDb.FavoritesDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: FavoritesRepository,
    favoritesDao: FavoritesDao
) :
    ViewModel() {

    private var _onFavoritesResponseLiveData: LiveData<List<Product>> = favoritesDao.getAll()
    fun getOnAllProductsLiveData(): LiveData<List<Product>> {
        return _onFavoritesResponseLiveData
    }

    fun removeFavorite(product: Product) {
        repository.removeFavorite(product)
    }
}