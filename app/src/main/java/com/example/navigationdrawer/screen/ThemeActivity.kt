package com.example.navigationdrawer.screen

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bac.simplecalculator.R
import com.bac.simplecalculator.databinding.ActivityThemeBinding
import com.example.navigationdrawer.Constant
import com.example.navigationdrawer.SharePreferenceUtils
import com.example.navigationdrawer.ThemeColor
import com.example.navigationdrawer.ThemeColorAdapter
import com.example.navigationdrawer.ThemeUtils

class ThemeActivity : AppCompatActivity() {

    private lateinit var adapter: ThemeColorAdapter
    private lateinit var binding: ActivityThemeBinding
    private var selectedTheme: String? = null

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeUtils.setAppTheme(this)
        super.onCreate(savedInstanceState)
        binding = ActivityThemeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.recyclerTheme

        val colorList = mutableListOf(
            ThemeColor(R.color.brown, Constant.BROWN, R.drawable.demo_theme_brown),
            ThemeColor(R.color.purple_700, Constant.PURPLE, R.drawable.demo_theme_purple),
            ThemeColor(R.color.green, Constant.GREEN, R.drawable.demo_theme_green),
            ThemeColor(R.color.blue, Constant.BLUE, R.drawable.demo_theme_blue),
            ThemeColor(R.color.yellow, Constant.YELLOW, R.drawable.demo_theme_yellow),
            ThemeColor(R.color.red, Constant.RED, R.drawable.demo_theme_red),
            ThemeColor(R.color.pink, Constant.PINK, R.drawable.demo_theme_pink),
            ThemeColor(R.color.orange, Constant.ORANGE, R.drawable.demo_theme_orange),
        )


        adapter = ThemeColorAdapter(colorList) { selectedItem, selectedIndex ->
            colorList.forEachIndexed { index, item ->
                item.isSelected = index == selectedIndex
                if (index == selectedIndex) {
                    binding.imgThemePreview.setBackgroundResource(item.imagePreview)
                }
            }
            selectedTheme = selectedItem.themeKey
            adapter.notifyDataSetChanged()
        }

        recyclerView.layoutManager = GridLayoutManager(this, 4)
        recyclerView.adapter = adapter

        binding.btnApplyTheme.setOnClickListener {
            selectedTheme?.let {
                SharePreferenceUtils.saveTheme(this, it)
                setResult(Activity.RESULT_OK, Intent().apply {
                    putExtra(Constant.THEME, it)
                })
                finish()
            }
        }
    }
}
