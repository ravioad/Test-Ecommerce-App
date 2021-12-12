package com.ravikumar.testapp.roomDb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ravikumar.testapp.models.CartItem
import com.ravikumar.testapp.models.Product

/**
 * SQLite Database for storing the logs.
 */
@Database(
    entities = [Product::class, CartItem::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
    abstract fun cartDao(): CartDao
}
