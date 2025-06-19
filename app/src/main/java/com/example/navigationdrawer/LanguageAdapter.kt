package com.example.navigationdrawer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.navigationdrawer.databinding.ItemLanguageBinding

class LanguageAdapter(
    private val items: List<LanguageItem>,
    private val onSelected: (LanguageItem) -> Unit
) : RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder>() {

    private var selectedPosition = -1

    inner class LanguageViewHolder(val binding: ItemLanguageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LanguageItem, isSelected: Boolean) {
            binding.languageName.text = item.name
            binding.flagImage.setImageResource(item.flagResId)
            binding.root.isSelected = isSelected

            binding.root.setOnClickListener {
                val prev = selectedPosition
                selectedPosition = adapterPosition
                notifyItemChanged(prev)
                notifyItemChanged(selectedPosition)
                onSelected(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val binding = ItemLanguageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LanguageViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.bind(items[position], position == selectedPosition)
    }
}
