package com.amwa.koky.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amwa.koky.R
import com.amwa.koky.databinding.ItemCategoryBinding
import com.amwa.koky.domain.model.Category
import java.util.*

class CategoryAdapter(
    private val interaction: Interaction
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var selectedCategory = "new"

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(
            oldItem: Category,
            newItem: Category
        ): Boolean = oldItem.key == newItem.key

        override fun areContentsTheSame(
            oldItem: Category,
            newItem: Category
        ): Boolean = oldItem == newItem
    })

    fun setCategory(key: String) {
        selectedCategory = key
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return CategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_category,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CategoryViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Category>) {
        differ.submitList(list)
    }

    inner class CategoryViewHolder(
        itemView: View,
        private val interaction: Interaction
    ) : RecyclerView.ViewHolder(itemView) {

        private val boldTypeface =
            ResourcesCompat.getFont(itemView.context, R.font.montserrat_semibold)
        private val normalTypeface =
            ResourcesCompat.getFont(itemView.context, R.font.montserrat_regular)

        private val binding = ItemCategoryBinding.bind(itemView)

        fun bind(item: Category) = binding.apply {
            root.setOnClickListener {
                interaction.onItemSelected(item)
            }

            if (item.key == selectedCategory) {
                root.typeface = boldTypeface
            } else {
                root.typeface = normalTypeface
            }
            root.text = item.category.toUpperCase(Locale.getDefault())
        }
    }

    interface Interaction {
        fun onItemSelected(item: Category)
    }
}