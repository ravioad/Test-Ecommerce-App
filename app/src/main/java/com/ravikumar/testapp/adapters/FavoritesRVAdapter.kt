package com.ravikumar.testapp.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.ravikumar.testapp.activities.ProductActivity
import com.ravikumar.testapp.databinding.FavoritesListItemBinding
import com.ravikumar.testapp.fragments.FavoritesFragment
import com.ravikumar.testapp.misc.Constants
import com.ravikumar.testapp.models.Product

class FavoritesRVAdapter(
    private val fragment: Fragment,
    private val list: ArrayList<Product>
) : RecyclerView.Adapter<FavoritesRVAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritesRVAdapter.MyViewHolder {
        return MyViewHolder(
            FavoritesListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoritesRVAdapter.MyViewHolder, position: Int) {
        val product = list[position]
        holder.bind(product)
        holder.itemView.setOnClickListener {
            val intent = Intent(fragment.requireContext(), ProductActivity::class.java).apply {
                putExtra(Constants.productIntent, Gson().toJson(list[position]))
            }
            fragment.startActivity(intent)
        }
        holder.binding.favoritesItemRemove.setOnClickListener {
            (fragment as? FavoritesFragment)?.removeFromFavorites(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(val binding: FavoritesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //        init {
//
//        }
        @SuppressLint("SetTextI18n")
        fun bind(product: Product) {
            Glide.with(fragment.requireContext()).load(product.image)
                .into(binding.favoritesImageView)
            binding.favoritesItemTitle.text = product.title
            binding.favoritesItemCategory.text =
                product.category.replaceFirstChar { it.uppercase() }
            binding.favoritesItemPrice.text = "$ ${product.price}"
        }
    }
}