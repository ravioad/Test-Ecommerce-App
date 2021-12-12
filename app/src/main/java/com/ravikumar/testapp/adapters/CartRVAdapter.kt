package com.ravikumar.testapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ravikumar.testapp.databinding.CartListItemBinding
import com.ravikumar.testapp.fragments.CartFragment
import com.ravikumar.testapp.models.CartItem

class CartRVAdapter(
    private val fragment: Fragment,
    private val list: ArrayList<CartItem>
) : RecyclerView.Adapter<CartRVAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartRVAdapter.MyViewHolder {
        return MyViewHolder(
            CartListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CartRVAdapter.MyViewHolder, position: Int) {
        val cartItem = list[position]
        holder.bind(cartItem)
        holder.binding.cartItemRemove.setOnClickListener {
            (fragment as? CartFragment)?.removeFromCart(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(val binding: CartListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(cartItem: CartItem) {
            Glide.with(fragment.requireContext()).load(cartItem.product.image)
                .into(binding.cartImageView)
            binding.cartItemTitle.text = cartItem.product.title
            binding.cartItemCategory.text =
                cartItem.product.category.replaceFirstChar { it.uppercase() }
            binding.cartItemPrice.text = "$ ${cartItem.totalPrice}"
            binding.cartItemCount.text = "x${cartItem.quantity}"
        }
    }
}