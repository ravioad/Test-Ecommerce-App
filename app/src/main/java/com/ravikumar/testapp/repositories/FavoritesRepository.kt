package com.ravikumar.testapp.repositories

import com.ravikumar.testapp.models.Product
import com.ravikumar.testapp.roomDb.LocalDataSource
import javax.inject.Inject

class FavoritesRepository @Inject constructor(private val localDataSource: LocalDataSource?) {

    fun removeFavorite(product: Product) {
        localDataSource?.removeFavorite(product)
    }

}