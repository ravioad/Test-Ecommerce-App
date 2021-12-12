package com.ravikumar.testapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ravikumar.testapp.models.CartItem
import com.ravikumar.testapp.repositories.CartRepository
import com.ravikumar.testapp.roomDb.CartDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CartRepository,
    cartDao: CartDao
) :
    ViewModel() {

    private var _onCartItemsResponseLiveData: LiveData<List<CartItem>> = cartDao.getAll()
    fun getOnCartItemsLiveData(): LiveData<List<CartItem>> {
        return _onCartItemsResponseLiveData
    }

    fun removeFromCart(cartItem: CartItem) {
        repository.removeFromCart(cartItem)
    }
}