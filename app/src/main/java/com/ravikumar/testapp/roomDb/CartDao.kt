package com.ravikumar.testapp.roomDb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ravikumar.testapp.models.CartItem

@Dao
interface CartDao {

    @Query("SELECT * FROM cart")
    fun getAll(): LiveData<List<CartItem>>

    @Query("SELECT EXISTS(SELECT * FROM cart WHERE id = :id)")
    fun isAlreadyAdded(id: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cartItem: CartItem): Long

    @Delete
    fun delete(cartItem: CartItem): Int

    @Query("DELETE FROM cart")
    fun deleteAll()
}
