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
//
//    @Query("SELECT * FROM recentVideos WHERE subjectId =:subjectID ORDER BY createAt DESC LIMIT 1")
//    fun getLatestWatchedVideoOfSubject(subjectID: String): LiveData<RecentWatchedVideo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: Product): Long
//
//    @Query("UPDATE recentVideos SET createAt = :createdAt, videoWatchedInSeconds = :videoWatchedInSeconds WHERE videoLessonId =:videoLessonId")
//    fun update(createdAt: Long, videoWatchedInSeconds: Long?, videoLessonId: String)

    @Delete
    fun delete(product: Product): Int

    @Query("DELETE FROM favorites")
    fun deleteAll()
//
//    @Query("SELECT COUNT() FROM recentVideos WHERE videoLessonId = :videoLessonId")
//    fun exists(videoLessonId: String): Int
}
