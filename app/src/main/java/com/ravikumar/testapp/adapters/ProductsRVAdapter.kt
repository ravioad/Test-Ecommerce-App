package com.ravikumar.testapp.adapters

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.ravikumar.testapp.activities.ProductActivity
import com.ravikumar.testapp.databinding.ProductItemLayoutBinding
import com.ravikumar.testapp.fragments.HomeFragment
import com.ravikumar.testapp.misc.Constants
import com.ravikumar.testapp.misc.makeGone
import com.ravikumar.testapp.misc.makeVisible
import com.ravikumar.testapp.models.Product

class ProductsRVAdapter(
    private val fragment: Fragment,
    private val list: List<Product>
) : RecyclerView.Adapter<ProductsRVAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductsRVAdapter.MyViewHolder {
        return MyViewHolder(
            ProductItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductsRVAdapter.MyViewHolder, position: Int) {
        val product = list[position]
        holder.bind(product)
        holder.itemView.setOnClickListener {
            val intent = Intent(fragment.requireContext(), ProductActivity::class.java).apply {
                putExtra(Constants.productIntent, Gson().toJson(list[position]))
            }
            fragment.startActivity(intent)
        }
        holder.binding.addFavorite.setOnClickListener {
            if (fragment is HomeFragment) fragment.addFavorite(product)
            holder.addFavoriteAnimation()
        }
        holder.binding.removeFavorite.setOnClickListener {
            if (fragment is HomeFragment) fragment.removeFavorite(product)
            holder.removeFavoriteAnimation()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(val binding: ProductItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

//        init {
//
//        }

        fun addFavoriteAnimation() {
            binding.addFavorite.makeGone()
            binding.removeFavorite.makeVisible()
            val animator = ObjectAnimator.ofInt(binding.removeFavorite.drawable, "level", 10000)
            animator.duration = 400
            animator.start()
        }

        fun removeFavoriteAnimation() {
            binding.addFavorite.makeVisible()
            val animator = ObjectAnimator.ofInt(binding.removeFavorite.drawable, "level", 0)
            animator.duration = 400
            animator.start()
            animator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {
                    binding.removeFavorite.visibility = View.GONE
                }

                override fun onAnimationCancel(animation: Animator?) {

                }

                override fun onAnimationRepeat(animation: Animator?) {

                }

            })
        }

        @SuppressLint("SetTextI18n")
        fun bind(product: Product) {
            Glide.with(fragment.requireContext()).load(product.image).into(binding.itemImage)
            binding.itemTitle.text = product.title
            binding.itemCategory.text = product.category.replaceFirstChar { it.uppercase() }
            binding.itemPrice.text = "$ ${product.price}"
            (fragment as? HomeFragment)?.isFavoriteAlreadyAdded(product.id) {
                if (it) {
                    addFavoriteAnimation()
                } else {
                    removeFavoriteAnimation()
                }
            }
        }
    }
}