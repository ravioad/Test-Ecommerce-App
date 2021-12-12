package com.ravikumar.testapp.repositories

import com.ravikumar.testapp.models.CartItem
import com.ravikumar.testapp.roomDb.LocalDataSource
import javax.inject.Inject

class ProductRepository @Inject constructor(private val localDataSource: LocalDataSource) {

    fun addToCart(cartItem: CartItem) {
        localDataSource.addToCart(cartItem)
    }

    fun removeFromCart(cartItem: CartItem) {
        localDataSource.removeFromCart(cartItem)
    }

}