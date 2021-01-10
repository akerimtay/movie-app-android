package com.akerimtay.movieapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.akerimtay.movieapp.R
import com.akerimtay.movieapp.data.model.Movie
import com.akerimtay.movieapp.databinding.ItemMovieBinding

class MoviesAdapter(
    private val onItemClickListener: (movie: Movie) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemMovieBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_movie, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (holder is MovieViewHolder) {
            holder.binding.apply {
                movie = item
                executePendingBindings()
                root.setOnClickListener { onItemClickListener.invoke(item) }
            }
        }
    }

    override fun getItemCount(): Int = items.size

    fun setItems(newItems: List<Movie>) {
        val callback = MovieCallback(items, newItems)
        val diffResult = DiffUtil.calculateDiff(callback)
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    class MovieViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)

}

class MovieCallback(
    private val oldItems: List<Movie>,
    private val newItems: List<Movie>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].id == newItems[newItemPosition].id
    }

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }

}