package com.ravikumar.testapp.repositories

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ravikumar.testapp.helperClasses.Resource
import com.ravikumar.testapp.misc.Constants
import com.ravikumar.testapp.misc.Urls
import com.ravikumar.testapp.misc.printLog
import com.ravikumar.testapp.models.Product
import com.ravikumar.testapp.roomDb.LocalDataSource
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.*
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.resume


class HomeRepository @Inject constructor(private val localDataSource: LocalDataSource?) {

    suspend fun getAllProducts(): Resource<List<Product>> =
        suspendCancellableCoroutine { coroutine ->
            val client = OkHttpClient().newBuilder()
                .build()
            val request: Request = Request.Builder()
                .url(Urls.allProductsUrl)
                .method("GET", null)
                .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    "getAllProductsOnFailure".printLog(e)
                    coroutine.resume(Resource.Error(e.message.toString()))
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseBody = response.body?.string()
                    if (response.isSuccessful) {
                        coroutine.resume(getListFromResponse(responseBody))
                    } else {
                        coroutine.resume(Resource.Error(Constants.unknownError))
                    }
                }
            })
        }

    fun getListFromResponse(response: String?): Resource<List<Product>> {
        return try {
            val list: List<Product> = Gson().fromJson(
                response,
                object : TypeToken<List<Product>>() {}.type
            )
            Resource.Success(list)
        } catch (e: Exception) {
            "getAllProducts".printLog(e)
            Resource.Error(Constants.unknownError)
        }
    }

    fun addFavorite(product: Product) {
        localDataSource?.addFavorite(product)
    }

    fun removeFavorite(product: Product) {
        localDataSource?.removeFavorite(product)
    }

    fun isAlreadyAdded(id: Int, callback: (Boolean) -> Unit) {
        localDataSource?.isAlreadyAdded(id, callback)
    }
}