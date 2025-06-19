package com.example.navigationdrawer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navigationdrawer.databinding.ActivityLanguageBinding

class LanguageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLanguageBinding
    private lateinit var adapter: LanguageAdapter
    private var selectedLanguageItem : LanguageItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        setupListeners()
    }
    private fun setupRecyclerView() {
        val languages = listOf(
            LanguageItem(getString(R.string.vietnamese), R.drawable.flag_vn, "vi"),
            LanguageItem(getString(R.string.english_us), R.drawable.flag_us, "en-US"),
            LanguageItem(getString(R.string.english_uk), R.drawable.flag_uk, "en-UK"),
            LanguageItem(getString(R.string.french), R.drawable.flag_fr, "fr"),
            LanguageItem(getString(R.string.german), R.drawable.flag_de, "de"),
            LanguageItem(getString(R.string.japanese), R.drawable.flag_jp, "ja"),
            LanguageItem(getString(R.string.korean), R.drawable.flag_kr, "ko"),
            LanguageItem(getString(R.string.portuguese), R.drawable.flag_pt, "pt"),
            LanguageItem(getString(R.string.spanish), R.drawable.flag_es, "es"),
            LanguageItem(getString(R.string.arabic), R.drawable.flag_ar, "ar"),
        )

        adapter = LanguageAdapter(languages) { selected ->
            binding.titleLanguage.text = selected.name
            selectedLanguageItem = selected
        }

        binding.languageRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.languageRecyclerView.adapter = adapter
    }

    private fun setupListeners() {
        binding.backButton.setOnClickListener {
            finish()
        }
        binding.applyButton.setOnClickListener {
            Toast.makeText(this, getString(R.string.selected) + " " + (selectedLanguageItem?.name ?: ""), Toast.LENGTH_SHORT).show()
            finish()
        }

    }
}