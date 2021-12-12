package com.ravikumar.testapp.repositories

import com.ravikumar.testapp.models.Product
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class HomeRepositoryTest {
    private lateinit var homeRepository: HomeRepository
    private lateinit var correctResponse: String

    @Before
    fun setupHomeRepository() {
        homeRepository = HomeRepository(null)
        correctResponse =
            "[{\"id\":1,\"title\":\"Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops\",\"price\":109.95,\"description\":\"Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday\",\"category\":\"men's clothing\",\"image\":\"https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg\",\"rating\":{\"rate\":3.9,\"count\":120}}]"
    }

    @Test
    fun getListFromResponse_throwsException_emptyString() {
        Assert.assertThrows(RuntimeException::class.java) {
            homeRepository.getListFromResponse("")
        }
    }

    @Test
    fun getListFromResponse_nullString() {
        Assert.assertThrows(RuntimeException::class.java) {
            homeRepository.getListFromResponse(null)
        }
    }

    @Test
    fun getListFromResponse_correctInput_sizeNotNull() {
        val result: List<Product>? = homeRepository.getListFromResponse(correctResponse).data
        Assert.assertNotEquals(null) {
            result?.size
        }
    }

    @Test
    fun getListFromResponse_correctInput_sizeNotZero() {
        val result: List<Product>? = homeRepository.getListFromResponse(correctResponse).data
        Assert.assertNotEquals(0) {
            result?.size
        }
    }
}