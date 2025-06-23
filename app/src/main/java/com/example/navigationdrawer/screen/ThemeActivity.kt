package com.example.navigationdrawer.screen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.navigationdrawer.Constant
import com.example.navigationdrawer.SharePreferenceUtils
import com.example.navigationdrawer.ThemeUtils
import com.example.navigationdrawer.databinding.ActivityThemeBinding

class ThemeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThemeBinding
    private var selectedTheme: String? = null
    private lateinit var borderMap: Map<String, View>

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeUtils.setAppTheme(this)
        super.onCreate(savedInstanceState)
        binding = ActivityThemeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBorders()
        setupClickEvents()

        binding.btnApplyTheme.setOnClickListener {
            selectedTheme?.let {
                SharePreferenceUtils.saveTheme(this, it)
                val resultIntent = Intent().apply {
                    putExtra(Constant.THEME, it)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    private fun setupBorders() {
        borderMap = mapOf(
            Constant.BROWN to binding.borderBlack,
            Constant.PURPLE to binding.borderPurple,
            Constant.GREEN to binding.borderGreen,
            Constant.BLUE to binding.borderBlue,
            Constant.YELLOW to binding.borderYellow,
            Constant.RED to binding.borderRed,
            Constant.PINK to binding.borderPink,
            Constant.ORANGE to binding.borderOrange
        )
    }

    private fun setupClickEvents() {
        binding.colorBlack.setOnClickListener { selectTheme(Constant.BROWN) }
        binding.colorPurple.setOnClickListener { selectTheme(Constant.PURPLE) }
        binding.colorGreen.setOnClickListener { selectTheme(Constant.GREEN) }
        binding.colorBlue.setOnClickListener { selectTheme(Constant.BLUE) }
        binding.colorYellow.setOnClickListener { selectTheme(Constant.YELLOW) }
        binding.colorRed.setOnClickListener { selectTheme(Constant.RED) }
        binding.colorPink.setOnClickListener { selectTheme(Constant.PINK) }
        binding.colorOrange.setOnClickListener { selectTheme(Constant.ORANGE) }

    }

    private fun selectTheme(theme: String) {
        selectedTheme = theme
        borderMap.values.forEach { it.visibility = View.GONE }
        borderMap[theme]?.visibility = View.VISIBLE
    }
}
