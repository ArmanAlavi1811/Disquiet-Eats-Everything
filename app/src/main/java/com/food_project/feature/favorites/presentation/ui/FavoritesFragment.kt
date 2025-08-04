package com.food_project.feature.favorites.presentation.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.food_project.application.SearchFocusable
import com.food_project.databinding.FragmentFavoritesBinding
import com.food_project.feature.favorites.presentation.ui.adapter.FavoritesAdapter
import com.food_project.feature.favorites.presentation.viewmodel.FavoritesViewModel
import com.food_project.feature.favorites.presentation.viewmodel.FavoritesViewModelFactory
import com.food_project.feature.how_to_screen.presentation.ui.HowToScreen

class FavoritesFragment : Fragment(), SearchFocusable {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel: FavoritesViewModel
    private lateinit var favoritesAdapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupRecyclerView()
        setupSearch()
        setupObservers()

        viewModel.loadFavorites()
    }

    private fun setupViewModel() {
        val factory = FavoritesViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[FavoritesViewModel::class.java]
    }

    private fun setupRecyclerView() {
        favoritesAdapter = FavoritesAdapter(
            onItemClick = { meal ->
                Intent(requireContext(), HowToScreen::class.java).also {
                    it.putExtra("mealID", meal.idMeal)
                    startActivity(it)
                }
            },
            onFavoriteClick = { meal ->
                viewModel.removeFavorite(meal)
            }
        )
        binding.rvFavorites.apply {
            adapter = favoritesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupSearch() {
        binding.searchViewFavorites.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchFavorites(newText.orEmpty())
                return true
            }
        })
    }

    private fun setupObservers() {
        viewModel.favoritesList.observe(viewLifecycleOwner) { favorites ->
            if (favorites.isEmpty()) {
                binding.rvFavorites.visibility = View.GONE
                binding.emptyStateView.visibility = View.VISIBLE
            } else {
                binding.rvFavorites.visibility = View.VISIBLE
                binding.emptyStateView.visibility = View.GONE
                favoritesAdapter.updateData(favorites)
            }
        }
    }

    override fun focusSearchView() {
        binding.searchViewFavorites.isIconified = false
        binding.searchViewFavorites.requestFocus()

        binding.searchViewFavorites.post {
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.searchViewFavorites, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}