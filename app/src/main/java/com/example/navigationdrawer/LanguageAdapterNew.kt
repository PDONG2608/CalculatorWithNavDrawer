package com.example.navigationdrawer

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.bac.simplecalculator.R
import com.bac.simplecalculator.databinding.ItemLanguageBinding

class LanguageAdapterNew(
    private val languages: MutableList<LanguageItem>,

    private val itemClick: ((LanguageItem, Int) -> Unit)
) : RecyclerView.Adapter<LanguageAdapterNew.LanguageViewHolder>() {
    var selectedPosition = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_language, parent, false)
        return LanguageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return languages.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        val item = languages[position]
        holder.bind(item)

        if (position == selectedPosition) {
            holder.binding.root.setBackgroundResource(R.drawable.bg_sub_selected)
        } else {
            holder.binding.root.setBackgroundResource(R.drawable.bg_sub_normal)
        }

        holder.itemView.setOnClickListener {
            notifyItemChanged(selectedPosition)
            selectedPosition = position
            notifyItemChanged(selectedPosition)
            itemClick.invoke(item, position)
        }
    }

    inner class LanguageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemLanguageBinding.bind(itemView)

        fun bind(item: LanguageItem) {
            binding.flagImage.setImageResource(item.flagResId)
            binding.languageName.text = item.name
            binding.tvCoin.text = item.coin.toString()

            if (item.coin == 0) {
                binding.llCoin.isGone = true
            } else {
                binding.llCoin.isGone = false
            }
        }
    }
}