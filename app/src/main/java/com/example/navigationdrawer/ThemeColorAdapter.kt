package com.example.navigationdrawer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bac.simplecalculator.R

class ThemeColorAdapter(
    private val colors: List<ThemeColor>,
    private val onClick: (ThemeColor, Int) -> Unit
) : RecyclerView.Adapter<ThemeColorAdapter.ColorViewHolder>() {

    inner class ColorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val viewColor: View = view.findViewById(R.id.viewColor)
        val viewBorder: View = view.findViewById(R.id.viewBorder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_theme_color, parent, false)
        return ColorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val item = colors[position]
        holder.viewColor.setBackgroundResource(item.colorResId)
        holder.viewBorder.visibility = if (item.isSelected) View.VISIBLE else View.GONE

        holder.itemView.setOnClickListener {
            onClick(item, position)
        }
    }

    override fun getItemCount() = colors.size
}
