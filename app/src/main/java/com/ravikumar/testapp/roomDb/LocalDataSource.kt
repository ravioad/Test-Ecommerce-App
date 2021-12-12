package com.ravikumar.testapp.roomDb

import android.os.Handler
import android.os.Looper
import com.ravikumar.testapp.models.Product
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val favoritesDao: FavoritesDao,
) {
    private val executorService: ExecutorService = Executors.newFixedThreadPool(4)
    private val mainThreadHandler by lazy {
        Handler(Looper.getMainLooper())
    }

    //
//    fun getAllPendingVideosProgresses(callback: (List<Product>) -> Unit) {
//        executorService.execute {
//            val videosProgresses = favoritesDao.getAll()
//            mainThreadHandler.post { callback(videosProgresses) }
//        }
//    }
//
//    fun getLastVideoWatched(subjectID: String): LiveData<RecentWatchedVideo> {
//        return recentWatchedVideoDao.getLatestWatchedVideoOfSubject(subjectID)
//    }
//
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
//
//    fun deleteAllRecentVideos() {
//        executorService.execute {
//            recentWatchedVideoDao.deleteAll()
//        }
//    }
//
//    fun deleteFirstRecentVideo(recentWatchedVideo: RecentWatchedVideo) {
//        executorService.execute {
//            recentWatchedVideoDao.delete(recentWatchedVideo)
//        }
//    }
//
//    fun addRecentVideo(recentWatchedVideo: RecentWatchedVideo) {
//        executorService.execute {
//            recentWatchedVideoDao.insert(recentWatchedVideo)
//        }
//    }
//
//
//    fun videoProgressExists(progressId: String, callback: (Boolean) -> Unit) {
//        executorService.execute {
//            val exists = videoProgressPersistenceModelDao.videoProgressExists(progressId)
//            mainThreadHandler.post { callback(exists) }
//        }
//    }
//
//    fun getProgressById(videoProgressId: String, callback: (Int) -> Unit) {
//        executorService.execute {
//            val exists = videoProgressPersistenceModelDao.getProgressById(videoProgressId)
//            mainThreadHandler.post { callback(exists) }
//        }
//    }
//
//    fun updateVideoProgress(
//        watchTimeInSeconds: Int,
//        videoProgressId: String,
//        callback: (Int) -> Unit
//    ) {
//        executorService.execute {
//            val updateStatus =
//                videoProgressPersistenceModelDao.update(watchTimeInSeconds, videoProgressId)
//            mainThreadHandler.post { callback(updateStatus) }
//        }
//    }
//
//    fun removeVideoProgress(videoProgressPersistenceModel: VideoProgressPersistenceModel) {
//        executorService.execute {
//            videoProgressPersistenceModelDao.delete(videoProgressPersistenceModel)
//        }
//    }
//
//    fun addVideoProgress(
//        videoProgressPersistenceModel: VideoProgressPersistenceModel,
//        callback: (Int) -> Unit
//    ) {
//        executorService.execute {
//            val insertedStatus =
//                videoProgressPersistenceModelDao.insert(videoProgressPersistenceModel)
//            mainThreadHandler.post { callback(insertedStatus.toInt()) }
//        }
//    }

}