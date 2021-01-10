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
import com.akerimtay.movieapp.utils.ScrollStateHolder
import com.akerimtay.movieapp.utils.SpaceItemDecoration

class CategoryWithMoviesAdapter(
    private val scrollStateHolder: ScrollStateHolder,
    private val onItemClickListener: (movie: Movie) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<CategoryWithMovies>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemCategoryWithMovieBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_category_with_movie, parent, false)
        val viewHolder =
            CategoryWithMoviesViewHolder(binding, scrollStateHolder, onItemClickListener)
        viewHolder.onCreated()
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (holder is CategoryWithMoviesViewHolder) {
            holder.onBind(item)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        if (holder is CategoryWithMoviesViewHolder) {
            holder.onRecycled()
        }
    }

    fun setItems(newItems: List<CategoryWithMovies>) {
        val callback = CategoryWithMoviesCallback(items, newItems)
        val diffResult = DiffUtil.calculateDiff(callback)
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    class CategoryWithMoviesViewHolder(
        private val binding: ItemCategoryWithMovieBinding,
        private val scrollStateHolder: ScrollStateHolder,
        private val onItemClickListener: (movie: Movie) -> Unit
    ) : RecyclerView.ViewHolder(binding.root), ScrollStateHolder.ScrollStateKeyProvider {

        private var moviesAdapter: MoviesAdapter? = null
        private var currentItem: CategoryWithMovies? = null

        private val spacing = binding.root.context.dpToPx(24)
        private val itemDecoration = SpaceItemDecoration(spacing, SpaceItemDecoration.HORIZONTAL)

        override fun getScrollStateKey(): String? = currentItem?.category?.name

        fun onCreated() {
            binding.moviesRecycler.apply {
                adapter = moviesAdapter
            }
            binding.moviesRecycler.let {
                scrollStateHolder.setupRecyclerView(it, this)
            }
        }

        fun onBind(item: CategoryWithMovies) {
            currentItem = item
            binding.categoryWithMovies = item

            moviesAdapter = MoviesAdapter {
                onItemClickListener.invoke(it)
            }

            binding.moviesRecycler.apply {
                adapter = moviesAdapter
                addItemDecoration(itemDecoration)
            }
            moviesAdapter?.setItems(item.movies)
            scrollStateHolder.restoreScrollState(binding.moviesRecycler, this)
        }

        fun onRecycled() {
            scrollStateHolder.saveScrollState(binding.moviesRecycler, this)
            currentItem = null
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