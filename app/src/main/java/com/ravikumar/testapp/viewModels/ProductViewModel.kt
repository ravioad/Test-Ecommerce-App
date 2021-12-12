package com.ravikumar.testapp.viewModels

import androidx.lifecycle.ViewModel
import com.ravikumar.testapp.models.CartItem
import com.ravikumar.testapp.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val repository: ProductRepository) :
    ViewModel() {

    fun addToCart(cartItem: CartItem) {
        repository.addToCart(cartItem)
    }

    fun removeFavorite(cartItem: CartItem) {
        repository.removeFromCart(cartItem)
    }
}