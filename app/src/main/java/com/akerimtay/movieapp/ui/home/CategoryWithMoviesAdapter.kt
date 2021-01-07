package com.akerimtay.movieapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.akerimtay.movieapp.R
import com.akerimtay.movieapp.data.model.CategoryWithMovies
import com.akerimtay.movieapp.data.model.Movie
import com.akerimtay.movieapp.databinding.ItemCategoryWithMovieBinding
import com.akerimtay.movieapp.extensions.dpToPx
import com.akerimtay.movieapp.utils.SpaceItemDecoration

class CategoryWithMoviesAdapter(
    private val onItemClickListener: (movie: Movie) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = mutableListOf<CategoryWithMovies>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemCategoryWithMovieBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_category_with_movie, parent, false)
        return CategoryWithMoviesViewHolder(binding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (holder is CategoryWithMoviesViewHolder) {
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int = items.size

    fun setItems(newItems: List<CategoryWithMovies>) {
        val callback = CategoryWithMoviesCallback(items, newItems)
        val diffResult = DiffUtil.calculateDiff(callback)
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    class CategoryWithMoviesViewHolder(
        private val binding: ItemCategoryWithMovieBinding,
        private val onItemClickListener: (movie: Movie) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var moviesAdapter: MoviesAdapter? = null

        fun bind(categoryWithMovies: CategoryWithMovies) {
            binding.categoryWithMovies = categoryWithMovies

            moviesAdapter = MoviesAdapter {
                onItemClickListener.invoke(it)
            }
            val spacing = binding.root.context.dpToPx(24)
            val itemDecoration = SpaceItemDecoration(spacing, SpaceItemDecoration.HORIZONTAL)
            binding.moviesRecycler.apply {
                adapter = moviesAdapter
                addItemDecoration(itemDecoration)
            }
            moviesAdapter?.setItems(categoryWithMovies.movies)
        }
    }
}

class CategoryWithMoviesCallback(
    private val oldItems: List<CategoryWithMovies>,
    private val newItems: List<CategoryWithMovies>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].category.id == newItems[newItemPosition].category.id
    }

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }
}