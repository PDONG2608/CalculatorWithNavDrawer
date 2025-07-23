package com.example.navigationdrawer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bac.simplecalculator.R

class LanguageAdapter(
    private val items: List<LanguageItem>,
    private val onSelected: (LanguageItem) -> Unit
) : RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder>() {

    private var selectedPosition = -1

    inner class LanguageViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val languageName: TextView = itemView.findViewById(R.id.languageName)
        private val flagImage: ImageView = itemView.findViewById(R.id.flagImage)

        fun bind(item: LanguageItem, isSelected: Boolean) {
            languageName.text = item.name
            flagImage.setImageResource(item.flagResId)
            itemView.isSelected = isSelected

            itemView.setOnClickListener {
                val prev = selectedPosition
                selectedPosition = adapterPosition
                notifyItemChanged(prev)
                notifyItemChanged(selectedPosition)
                onSelected(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_language, parent, false)
        return LanguageViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.bind(items[position], position == selectedPosition)
    }
}