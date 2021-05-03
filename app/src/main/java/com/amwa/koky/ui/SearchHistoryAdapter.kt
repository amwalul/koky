package com.amwa.koky.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amwa.koky.R
import com.amwa.koky.databinding.ItemSearchHistoryBinding
import com.amwa.koky.domain.model.SearchHistory

class SearchHistoryAdapter(
    private val interaction: Interaction
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<SearchHistory>() {
        override fun areItemsTheSame(oldItem: SearchHistory, newItem: SearchHistory) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: SearchHistory, newItem: SearchHistory) =
            oldItem == newItem
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return HistoryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_search_history,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HistoryViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<SearchHistory>) {
        differ.submitList(list)
    }

    inner class HistoryViewHolder(
        itemView: View,
        private val interaction: Interaction
    ) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemSearchHistoryBinding.bind(itemView)

        fun bind(item: SearchHistory) = binding.apply {
            root.setOnClickListener {
                interaction.onItemSelected(item)
            }

            ivDelete.setOnClickListener {
                interaction.onDeleteClicked(item)
            }

            tvHistory.text = item.query
        }
    }

    interface Interaction {
        fun onItemSelected(item: SearchHistory)
        fun onDeleteClicked(item: SearchHistory)
    }
}