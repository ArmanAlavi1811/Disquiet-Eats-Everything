package com.food_project.feature.favorites.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.food_project.databinding.ItemFavoriteMealBinding
import com.food_project.feature.home.domain.data.model.FavoriteMealEntity

class FavoritesAdapter(
    private val onItemClick: (FavoriteMealEntity) -> Unit,
    private val onFavoriteClick: (FavoriteMealEntity) -> Unit
) : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    private var items: List<FavoriteMealEntity> = emptyList()

    fun updateData(newItems: List<FavoriteMealEntity>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFavoriteMealBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: ItemFavoriteMealBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onItemClick(items[adapterPosition])
                }
            }

            binding.btnUnfavorite.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onFavoriteClick(items[adapterPosition])
                }
            }
        }

        fun bind(item: FavoriteMealEntity) {
            binding.tvMealName.text = item.strMeal
            binding.tvMealCategory.text = item.strCategory

            Glide.with(binding.root.context)
                .load(item.strMealThumb)
                .into(binding.ivMealImage)
        }
    }
}