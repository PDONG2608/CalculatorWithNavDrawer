package com.example.navigationdrawer.screen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.navigationdrawer.Constant
import com.example.navigationdrawer.SharePreferenceUtils
import com.example.navigationdrawer.databinding.ActivityThemeBinding

class ThemeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThemeBinding
    private var selectedTheme: String? = null
    private lateinit var borderMap: Map<String, View>

    override fun onCreate(savedInstanceState: Bundle?) {
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
            "black" to binding.borderBlack,
            "purple" to binding.borderPurple,
            "green" to binding.borderGreen,
            "blue" to binding.borderBlue,
            "yellow" to binding.borderYellow,
            "red" to binding.borderRed,
            "pink" to binding.borderPink,
            "orange" to binding.borderOrange
        )
    }

    private fun setupClickEvents() {
        binding.colorBlack.setOnClickListener { selectTheme("black") }
        binding.colorPurple.setOnClickListener { selectTheme("purple") }
        binding.colorGreen.setOnClickListener { selectTheme("green") }
        binding.colorBlue.setOnClickListener { selectTheme("blue") }
        binding.colorYellow.setOnClickListener { selectTheme("yellow") }
        binding.colorRed.setOnClickListener { selectTheme("red") }
        binding.colorPink.setOnClickListener { selectTheme("pink") }
        binding.colorOrange.setOnClickListener { selectTheme("orange") }
    }

    private fun selectTheme(theme: String) {
        selectedTheme = theme
        borderMap.values.forEach { it.visibility = View.GONE }
        borderMap[theme]?.visibility = View.VISIBLE
    }
}
