package com.akerimtay.movieapp.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.akerimtay.movieapp.R
import com.akerimtay.movieapp.data.model.Video
import com.akerimtay.movieapp.databinding.ItemVideoBinding

class VideosAdapter(
    private val onItemClickListener: (video: Video) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<Video>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemVideoBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_video, parent, false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (holder is VideoViewHolder) {
            holder.binding.apply {
                video = item
                executePendingBindings()
                root.setOnClickListener { onItemClickListener.invoke(item) }
            }
        }
    }

    override fun getItemCount(): Int = items.size

    fun setItems(newItems: List<Video>) {
        val callback = VideoCallback(items, newItems)
        val diffResult = DiffUtil.calculateDiff(callback)
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    class VideoViewHolder(val binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root)

}

class VideoCallback(
    private val oldItems: List<Video>,
    private val newItems: List<Video>
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