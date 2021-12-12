package com.ravikumar.testapp.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartItem(
    @PrimaryKey val cartItemId: Int,
    val quantity: Int,
    val totalPrice: Double,
    @Embedded val product: Product,
)