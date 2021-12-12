package com.ravikumar.testapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.ravikumar.testapp.R
import com.ravikumar.testapp.adapters.ProductsRVAdapter
import com.ravikumar.testapp.databinding.CategoryChipLayoutBinding
import com.ravikumar.testapp.databinding.FragmentHomeBinding
import com.ravikumar.testapp.helperClasses.Resource
import com.ravikumar.testapp.misc.makeGone
import com.ravikumar.testapp.misc.makeVisible
import com.ravikumar.testapp.models.Product
import com.ravikumar.testapp.viewModels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("NotifyDataSetChanged")
@AndroidEntryPoint
class HomeFragment : Fragment(), ChipGroup.OnCheckedChangeListener {
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private var _binding: FragmentHomeBinding? = null
    private var productsList: List<Product>? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        addOnAllProductsResponseLiveDataObserver()
        viewModel.getAllProducts()
        binding.homeChipGroup.setOnCheckedChangeListener(this)
        return binding.root
    }

    private fun addOnAllProductsResponseLiveDataObserver() {
        viewModel.getOnAllProductsLiveData().observe(viewLifecycleOwner, { result ->
            when (result) {
                is Resource.Success -> {
                    hideLoading()
                    productsList = result.data
                    addCategories(result.data!!.map { it.category }.distinct())
                    setRecyclerViewAdapter(productsList)
                }
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Error -> {
                    hideLoading()
                }
            }
        })
    }

    private fun setRecyclerViewAdapter(list: List<Product>?) {
        binding.recyclerView.apply {
            adapter = ProductsRVAdapter(this@HomeFragment, list ?: listOf())
            layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        }
    }

    private fun showLoading() {
        binding.homeContentLayout.makeGone()
        binding.homeShimmerLayout.makeVisible()
        binding.homeShimmerLayout.startShimmer()
    }

    private fun hideLoading() {
        binding.homeContentLayout.makeVisible()
        binding.homeShimmerLayout.makeGone()
        binding.homeShimmerLayout.stopShimmer()
    }

    private fun addCategories(list: List<String>) {
        addChip(getString(R.string.all), true)
        list.forEach { addChip(it) }
    }

    private fun addChip(title: String, isFirst: Boolean = false) {
        val chipBinding = CategoryChipLayoutBinding.inflate(
            LayoutInflater.from(requireContext()),
            binding.homeChipGroup,
            true
        )
        val chip = chipBinding.root
        chip.isChecked = isFirst
        chip.text = title.replaceFirstChar { it.uppercase() }
        chip.tag = title.lowercase()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCheckedChanged(group: ChipGroup?, checkedId: Int) {
        try {
            val chipTag: String = (group?.findViewById<Chip>(checkedId)?.tag as String?) ?: ""
            productsList?.let {
                val filteredList = it.filter { product -> product.category.lowercase() == chipTag }
                setRecyclerViewAdapter(if (filteredList.isNotEmpty()) filteredList else it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun addFavorite(product: Product) {
        viewModel.addFavorite(product)
    }

    fun removeFavorite(product: Product) {
        viewModel.removeFavorite(product)
    }

    fun isFavoriteAlreadyAdded(id: Int, callback: (Boolean) -> Unit) {
        viewModel.isAlreadyAdded(id, callback)
    }
}