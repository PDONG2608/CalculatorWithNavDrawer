package com.example.navigationdrawer

import android.os.Bundle
import androidx.core.view.isGone
import com.bac.simplecalculator.R
import com.bac.simplecalculator.databinding.ActivityLanguageNewBinding

class LanguageNewActivity : BaseActivity() {

    private lateinit var binding: ActivityLanguageNewBinding
    private lateinit var language: LanguageItem
    private lateinit var languageAdapterNew: LanguageAdapterNew

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageNewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAdapter()
        initListeners()
    }

    private fun initListeners() {
        binding.applyButton.setOnClickListener {

        }
    }

    private fun languageList(): MutableList<LanguageItem> {
        val languageList = mutableListOf<LanguageItem>()
        languageList.add(LanguageItem(getString(R.string.vietnamese), R.drawable.flag_vn, "vi", 0))
        languageList.add(LanguageItem(getString(R.string.english), R.drawable.flag_us, "en", 0))
        languageList.add(LanguageItem(getString(R.string.french), R.drawable.flag_fr, "fr", 100))
        languageList.add(LanguageItem(getString(R.string.german), R.drawable.flag_de, "de", 100))
        languageList.add(LanguageItem(getString(R.string.japanese), R.drawable.flag_jp, "ja", 200))
        languageList.add(LanguageItem(getString(R.string.korean), R.drawable.flag_kr, "ko", 250))
        languageList.add(LanguageItem(getString(R.string.portuguese), R.drawable.flag_pt, "pt", 350))
        languageList.add(LanguageItem(getString(R.string.spanish), R.drawable.flag_es, "es", 400))
        languageList.add(LanguageItem(getString(R.string.arabic), R.drawable.flag_ar, "ar", 500))
        return languageList
    }

    private fun initAdapter() {
        val allLanguageList = languageList()
        var itemIndex = allLanguageList.indexOfFirst { it.code == dLocale?.language }

        if (itemIndex != -1) {
            this.language = allLanguageList[itemIndex]
        } else {
            itemIndex = 0
            this.language = allLanguageList[itemIndex]
        }
        allLanguageList.removeAt(itemIndex)

        languageAdapterNew = LanguageAdapterNew(allLanguageList, itemClick = { it, _ ->
            this.language = it
            binding.applyButton.isGone = false
        })

        binding.languageRecyclerView.adapter = languageAdapterNew
    }
}