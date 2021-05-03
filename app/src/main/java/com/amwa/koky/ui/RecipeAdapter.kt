package com.amwa.koky.ui

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amwa.koky.R
import com.amwa.koky.databinding.ItemRecipeBinding
import com.amwa.koky.domain.model.Recipe
import com.bumptech.glide.Glide

class RecipeAdapter(
    private val interaction: Interaction
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(
            oldItem: Recipe,
            newItem: Recipe
        ): Boolean = oldItem.key == newItem.key

        override fun areContentsTheSame(
            oldItem: Recipe,
            newItem: Recipe
        ): Boolean = oldItem == newItem
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecipeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_recipe,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RecipeViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Recipe>) {
        differ.submitList(list)
    }

    inner class RecipeViewHolder(
        itemView: View,
        private val interaction: Interaction
    ) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemRecipeBinding.bind(itemView)

        private val placeholderResource = ContextCompat.getColor(itemView.context, R.color.green)
        private val placeholder = ColorDrawable(placeholderResource)

        fun bind(item: Recipe) = binding.apply {
            root.setOnClickListener {
                interaction.onItemSelected(item)
            }

            Glide.with(root.context)
                .load(item.thumb)
                .placeholder(placeholder)
                .into(ivPicture)

            tvName.text = item.title
        }
    }

    interface Interaction {
        fun onItemSelected(item: Recipe)
    }
}