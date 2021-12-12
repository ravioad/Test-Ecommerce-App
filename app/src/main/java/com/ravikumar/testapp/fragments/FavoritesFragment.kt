package com.ravikumar.testapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ravikumar.testapp.adapters.FavoritesRVAdapter
import com.ravikumar.testapp.databinding.FragmentFavoritesBinding
import com.ravikumar.testapp.misc.makeGone
import com.ravikumar.testapp.misc.makeVisible
import com.ravikumar.testapp.misc.printLog
import com.ravikumar.testapp.models.Product
import com.ravikumar.testapp.viewModels.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private val viewModel: FavoritesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private var _binding: FragmentFavoritesBinding? = null
    private lateinit var favoritesAdapter: FavoritesRVAdapter
    private lateinit var list: ArrayList<Product>

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        addOnFavoritesResponseLiveDataObserver()
        return binding.root
    }

    private fun addOnFavoritesResponseLiveDataObserver() {
        viewModel.getOnAllProductsLiveData().observe(viewLifecycleOwner, {
            "Favorites".printLog(it.size)
            list = ArrayList(it)
            setupDataToRecyclerView()

        })
    }

    private fun setupDataToRecyclerView() {
        if (list.isEmpty()) {
            binding.emptyFavoritesText.makeVisible()
            binding.recyclerView.makeGone()
            return
        }
        favoritesAdapter = FavoritesRVAdapter(this@FavoritesFragment, list)
        setRecyclerViewAdapter()
        addSwipeToDelete()
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
                removeFromFavorites(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    fun removeFromFavorites(position: Int) {
        viewModel.removeFavorite(list[position])
        list.removeAt(position)
        favoritesAdapter.notifyItemRemoved(position)
    }

    private fun setRecyclerViewAdapter() {
        binding.recyclerView.apply {
            adapter = favoritesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}