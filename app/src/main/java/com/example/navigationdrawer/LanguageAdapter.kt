package com.example.navigationdrawer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bac.simplecalculator.R

class LanguageAdapter(
    private val context: Context,
    private val items: List<LanguageItem>,
    private val onSelected: (LanguageItem) -> Unit
) : RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder>() {

    private var selectedPosition = items.indexOfFirst {
        it.code == LanguageUtils.getLocaleCode(context)
    }

    inner class LanguageViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val itemLayout = itemView.findViewById<View>(R.id.item_layout)
        private val languageName: TextView = itemView.findViewById(R.id.languageName)
        private val flagImage: ImageView = itemView.findViewById(R.id.flagImage)
//        private val icPremium: ImageView = itemView.findViewById(R.id.icon_premium)

        fun bind(item: LanguageItem, isSelected: Boolean) {
            languageName.text = item.name
            flagImage.setImageResource(item.flagResId)
            itemView.isSelected = isSelected
//            icPremium.visibility = if (item.isPremium) View.VISIBLE else View.GONE
//            itemLayout.alpha = if (item.isPremium) 0.5f else 1f

            itemView.setOnClickListener {
//                if (item.isPremium) return@setOnClickListener
                if (adapterPosition != RecyclerView.NO_POSITION && selectedPosition != adapterPosition) {
                    val prev = selectedPosition
                    selectedPosition = adapterPosition
                    notifyItemChanged(prev)
                    notifyItemChanged(selectedPosition)
                    onSelected(item)
                }
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