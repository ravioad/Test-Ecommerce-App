package com.ravikumar.testapp.roomDb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ravikumar.testapp.models.Product

/**
 * Data access object to query the database.
 */
@Dao
interface FavoritesDao {

    @Query("SELECT * FROM favorites")
    fun getAll(): LiveData<List<Product>>

    @Query("SELECT EXISTS(SELECT * FROM favorites WHERE id = :id)")
    fun isAlreadyAdded(id: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: Product): Long

    @Delete
    fun delete(product: Product): Int

    @Query("DELETE FROM favorites")
    fun deleteAll()
}
