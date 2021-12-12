package com.ravikumar.testapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ravikumar.testapp.adapters.CartRVAdapter
import com.ravikumar.testapp.databinding.FragmentCartBinding
import com.ravikumar.testapp.misc.*
import com.ravikumar.testapp.models.CartItem
import com.ravikumar.testapp.viewModels.CartViewModel
import dagger.hilt.android.AndroidEntryPoint


@SuppressLint("SetTextI18n")
@AndroidEntryPoint
class CartFragment : Fragment() {

    private val viewModel: CartViewModel by viewModels()
    private var _binding: FragmentCartBinding? = null
    private lateinit var cartAdapter: CartRVAdapter
    private lateinit var list: ArrayList<CartItem>

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        addOnFavoritesResponseLiveDataObserver()
        return binding.root
    }

    private fun addOnFavoritesResponseLiveDataObserver() {
        viewModel.getOnCartItemsLiveData().observe(viewLifecycleOwner, {
            "Observed!!!".printLog("TRUE")
            list = ArrayList(it)
            cartAdapter = CartRVAdapter(this@CartFragment, list)
            setRecyclerViewAdapter()
            addSwipeToDelete()
            setupCartDetails()
        })
    }

    private fun addSwipeToDelete() {
        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                //Remove swiped item from list and notify the RecyclerView
                removeFromCart(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    private fun setupCartDetails() {
        if (list.isEmpty()) {
            binding.emptyCartText.makeVisible()
            binding.recyclerView.makeGone()
            binding.cartContentLayout.makeGone()
            return
        }
        val totalSum = list.sumOf { it.totalPrice }
        binding.cartSubtotal.text = "$${totalSum.roundTo2Places()}"
        binding.cartShipping.text = "$${Constants.shippingPrice}"
        binding.cartTotal.text = "$${(totalSum + Constants.shippingPrice).roundTo2Places()}"
        binding.cartItemCount.text = "(${list.sumOf { it.quantity }} items)"
    }

    private fun setRecyclerViewAdapter() {
        binding.recyclerView.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    fun removeFromCart(position: Int) {
        viewModel.removeFromCart(list[position])
        list.removeAt(position)
        cartAdapter.notifyItemRemoved(position)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}