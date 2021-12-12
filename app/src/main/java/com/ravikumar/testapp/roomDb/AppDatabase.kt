package com.ravikumar.testapp.roomDb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ravikumar.testapp.models.Product

/**
 * SQLite Database for storing the logs.
 */
@Database(
    entities = [Product::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
}
