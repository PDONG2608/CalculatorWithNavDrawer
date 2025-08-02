package com.example.navigationdrawer

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isGone
import com.bac.simplecalculator.R
import com.bac.simplecalculator.databinding.ActivityLanguageNewBinding

class LanguageNewActivity : BaseActivity() {

    private lateinit var binding: ActivityLanguageNewBinding
    private lateinit var language: LanguageItem
    private lateinit var languageAdapterNew: LanguageAdapterNew
    private var listCoins = mutableListOf<Int>()
    private var coins = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageNewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listCoins = MySharedPreferences.getInstance().getListInt(Constants.SAVE_LIST_COINS, this) as MutableList<Int>
        initAdapter()
        initListeners()
    }

    private fun initListeners() {
        binding.applyButton.setOnClickListener {
            val saveCoin = MySharedPreferences.getInstance().getIntData(Constants.SAVE_GOLD)
            if (saveCoin >= language.coin) {
                LanguageUtils.setLocale(this, language.code)
                MySharedPreferences.getInstance().saveData(Constants.LANGUAGE_CURRENT_CODE, language.code)
                listCoins[language.id] = 0
                val remainingCoins = coins - language.coin
                MySharedPreferences.getInstance().saveData(Constants.SAVE_GOLD, remainingCoins)
                binding.gold.text = remainingCoins.toString()
                MySharedPreferences.getInstance().saveListInt(listCoins, Constants.SAVE_LIST_COINS, this)
            } else {
                Toast.makeText(this, R.string.insufficient_gold, Toast.LENGTH_SHORT).show()
            }
        }

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.tvGem.setOnClickListener {
            startActivity(Intent(this, ShopActivity::class.java))
        }
    }

    private fun languageList(): MutableList<LanguageItem> {
        val languageMeta = listOf(
            Triple(R.string.vietnamese, R.drawable.flag_vn, "vi"),
            Triple(R.string.english, R.drawable.flag_us, "en"),
            Triple(R.string.french, R.drawable.flag_fr, "fr"),
            Triple(R.string.german, R.drawable.flag_de, "de"),
            Triple(R.string.japanese, R.drawable.flag_jp, "ja"),
            Triple(R.string.korean, R.drawable.flag_kr, "ko"),
            Triple(R.string.portuguese, R.drawable.flag_pt, "pt"),
            Triple(R.string.spanish, R.drawable.flag_es, "es")
        )

        return languageMeta.mapIndexed { index, (nameRes, flagRes, code) ->
            LanguageItem(
                id = index,
                name = getString(nameRes),
                flagResId = flagRes,
                code = code,
                coin = listCoins.getOrElse(index) { 0 }
            )
        }.toMutableList()
    }

    private fun initAdapter() {
        val allLanguageList = languageList()
        languageAdapterNew = LanguageAdapterNew(allLanguageList, itemClick = { it, _ ->
            this.language = it
            binding.applyButton.isGone = false
        })
        binding.languageRecyclerView.adapter = languageAdapterNew
    }

    override fun onResume() {
        super.onResume()
        coins = MySharedPreferences.getInstance().getIntData(Constants.SAVE_GOLD)
        binding.gold.text = coins.toString()
    }
}