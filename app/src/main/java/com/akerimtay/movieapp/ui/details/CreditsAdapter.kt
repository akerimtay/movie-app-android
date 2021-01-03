package com.akerimtay.movieapp.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.akerimtay.movieapp.R
import com.akerimtay.movieapp.data.model.Credit
import com.akerimtay.movieapp.databinding.ItemCreditBinding

class CreditsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = mutableListOf<Credit>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemCreditBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_credit, parent, false)
        return CreditViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (holder is CreditViewHolder) {
            holder.binding.apply {
                credit = item
                executePendingBindings()
            }
        }
    }

    override fun getItemCount(): Int = items.size

    fun setItems(newItems: List<Credit>) {
        val callback = CreditCallback(items, newItems)
        val diffResult = DiffUtil.calculateDiff(callback)
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    class CreditViewHolder(val binding: ItemCreditBinding) : RecyclerView.ViewHolder(binding.root)
}

class CreditCallback(
    private val oldItems: List<Credit>,
    private val newItems: List<Credit>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].name == newItems[newItemPosition].name
    }

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }
}