package com.ravikumar.testapp.roomDb

import android.os.Handler
import android.os.Looper
import com.ravikumar.testapp.models.CartItem
import com.ravikumar.testapp.models.Product
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val favoritesDao: FavoritesDao,
    private val cartDao: CartDao,
) {
    private val executorService: ExecutorService = Executors.newFixedThreadPool(4)
    private val mainThreadHandler by lazy {
        Handler(Looper.getMainLooper())
    }

    fun isAlreadyAdded(id: Int, callback: (Boolean) -> Unit) {
        executorService.execute {
            val isAlreadyAdded = favoritesDao.isAlreadyAdded(id)
            mainThreadHandler.post { callback(isAlreadyAdded) }
        }
    }

    fun addToCart(cartItem: CartItem) {
        executorService.execute {
            cartDao.insert(cartItem)
        }
    }

    fun removeFromCart(cartItem: CartItem) {
        executorService.execute {
            cartDao.delete(cartItem)
        }
    }

    fun addFavorite(product: Product) {
        executorService.execute {
            favoritesDao.insert(product)
        }
    }

    fun removeFavorite(product: Product) {
        executorService.execute {
            favoritesDao.delete(product)
        }
    }
}