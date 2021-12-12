package com.ravikumar.testapp.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.ravikumar.testapp.R
import com.ravikumar.testapp.databinding.ActivityProductBinding
import com.ravikumar.testapp.misc.Constants
import com.ravikumar.testapp.misc.setWhiteStatusBarColor
import com.ravikumar.testapp.misc.showToastShort
import com.ravikumar.testapp.models.CartItem
import com.ravikumar.testapp.models.Product
import com.ravikumar.testapp.viewModels.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityProductBinding
    private val viewModel: ProductViewModel by viewModels()
    private var product: Product? = null
    private var count = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setWhiteStatusBarColor()
        setProductDetails()
        binding.productBackButton.setOnClickListener(this)
        binding.productAddButton.setOnClickListener(this)
        binding.productRemoveButton.setOnClickListener(this)
        binding.addToCartButton.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    private fun setProductDetails() {
        try {
            val productJson = intent.getStringExtra(Constants.productIntent)
            product = Gson().fromJson(productJson, Product::class.java)
            Glide.with(this).load(product?.image).into(binding.productImageView)
            binding.productTitle.text = product?.title
            binding.productDescription.text = product?.description
            binding.productRating.text = product?.rating?.rate.toString()
            binding.productPrice.text = "$ ${product?.price}"
            updateCountTextView()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.addToCartButton -> {
                addToCart()
            }
            R.id.productAddButton -> {
                count += 1
                updateCountTextView()
            }
            R.id.productRemoveButton -> {
                if (count > 1) {
                    count -= 1
                    updateCountTextView()
                }
            }
            R.id.productBackButton -> {
                onBackPressed()
            }
        }
    }

    private fun addToCart() {
        product?.let {
            val cartItem = CartItem(it.id, count, it.price * count, it)
            viewModel.addToCart(cartItem)
            "Item Added to Cart!".showToastShort(this)
            finish()
        }
    }

    private fun updateCountTextView() {
        binding.productCount.text = count.toString()
    }
}