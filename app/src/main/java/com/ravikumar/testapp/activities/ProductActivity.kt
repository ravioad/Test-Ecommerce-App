package com.ravikumar.testapp.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.ravikumar.testapp.R
import com.ravikumar.testapp.databinding.ActivityProductBinding
import com.ravikumar.testapp.misc.Constants
import com.ravikumar.testapp.misc.setWhiteStatusBarColor
import com.ravikumar.testapp.models.Product

class ProductActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setWhiteStatusBarColor()
        setProductDetails()
        binding.productBackButton.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    private fun setProductDetails() {
        try {
            val productJson = intent.getStringExtra(Constants.productIntent)
            val product: Product? = Gson().fromJson(productJson, Product::class.java)
            Glide.with(this).load(product?.image).into(binding.productImageView)
            binding.productTitle.text = product?.title
            binding.productDescription.text = product?.description
            binding.productRating.text = product?.rating?.rate.toString()
            binding.productPrice.text = "$ ${product?.price}"
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.productBackButton -> {
                onBackPressed()
            }
        }
    }
}