package com.amwa.koky.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amwa.koky.R
import com.amwa.koky.databinding.ItemStepNumberBinding

class StepNumberAdapter(
    private val interaction: Interaction
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var selectedStep = 1

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int) = oldItem == newItem

        override fun areContentsTheSame(oldItem: Int, newItem: Int) = oldItem == newItem
    })

    fun setSelectedStep(step: Int) {
        selectedStep = step
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return StepNumberViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_step_number,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is StepNumberViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Int>) {
        differ.submitList(list)
    }

    inner class StepNumberViewHolder(
        itemView: View,
        private val interaction: Interaction
    ) : RecyclerView.ViewHolder(itemView) {

        private val boldTypeface =
            ResourcesCompat.getFont(itemView.context, R.font.montserrat_semibold)
        private val normalTypeface =
            ResourcesCompat.getFont(itemView.context, R.font.montserrat_regular)

        private val binding = ItemStepNumberBinding.bind(itemView)

        fun bind(item: Int) = binding.apply {
            root.setOnClickListener {
                interaction.onItemSelected(item)
            }

            tvStepNumber.text = item.toString()

            if (item == selectedStep) {
                tvStepNumber.setBackgroundResource(R.drawable.bg_circle_border)
                tvStepNumber.typeface = boldTypeface
            } else {
                tvStepNumber.background = null
                tvStepNumber.typeface = normalTypeface
            }
        }
    }

    interface Interaction {
        fun onItemSelected(item: Int)
    }
}