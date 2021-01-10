package com.akerimtay.movieapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.akerimtay.movieapp.R
import com.akerimtay.movieapp.data.datasource.paging.NetworkState
import com.akerimtay.movieapp.data.model.Movie
import com.akerimtay.movieapp.databinding.ItemMovieLongBinding
import com.akerimtay.movieapp.databinding.ItemNetworkStateBinding

class MovieLongAdapter(
    private val onItemClickListener: OnMovieClickListener
) : PagedListAdapter<Movie, RecyclerView.ViewHolder>(MovieItemCallback()) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_NETWORK_STATE -> {
                val binding: ItemNetworkStateBinding =
                    DataBindingUtil.inflate(inflater, R.layout.item_network_state, parent, false)
                NetworkStateViewHolder(binding)
            }
            else -> {
                val binding: ItemMovieLongBinding =
                    DataBindingUtil.inflate(inflater, R.layout.item_movie_long, parent, false)
                MovieLongViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is MovieLongViewHolder -> {
                holder.binding.apply {
                    movie = item
                    executePendingBindings()
                    item?.let { root.setOnClickListener { onItemClickListener.onMovieClick(item) } }
                }
            }
            is NetworkStateViewHolder -> {
                holder.binding.apply {
                    state = networkState
                    executePendingBindings()
                    btnRetry.setOnClickListener { onItemClickListener.onClickRetry() }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            TYPE_NETWORK_STATE
        } else {
            TYPE_ITEM
        }
    }

    override fun getItemCount(): Int {
        onItemClickListener.whenListIsUpdated(super.getItemCount(), this.networkState)
        return super.getItemCount()
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.SUCCESS

    fun updateNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    class MovieLongViewHolder(val binding: ItemMovieLongBinding) :
        RecyclerView.ViewHolder(binding.root)

    class NetworkStateViewHolder(val binding: ItemNetworkStateBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnMovieClickListener {
        fun onClickRetry()

        fun whenListIsUpdated(size: Int, networkState: NetworkState?)

        fun onMovieClick(movie: Movie)
    }

    companion object {
        private const val TYPE_ITEM = 0
        private const val TYPE_NETWORK_STATE = 1
    }

}

class MovieItemCallback : DiffUtil.ItemCallback<Movie>() {

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem.id == newItem.id

}