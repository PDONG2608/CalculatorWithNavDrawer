package com.example.navigationdrawer.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navigationdrawer.Constant
import com.example.navigationdrawer.LanguageAdapter
import com.example.navigationdrawer.LanguageItem
import com.example.navigationdrawer.LanguageUtils
import com.example.navigationdrawer.R
import com.example.navigationdrawer.SharePreferenceUtils
import com.example.navigationdrawer.ThemeUtils
import com.example.navigationdrawer.databinding.ActivityLanguageBinding

class LanguageActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityLanguageBinding
    private lateinit var mAdapter: LanguageAdapter
    private var mSelectedLanguageItem: LanguageItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeUtils.setAppTheme(this)
        super.onCreate(savedInstanceState)
        mBinding = ActivityLanguageBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setupRecyclerView()
        setupListeners()
    }

    private fun setupRecyclerView() {
        val languages = listOf(
            LanguageItem(getString(R.string.vietnamese), R.drawable.flag_vn, "vi"),
            LanguageItem(getString(R.string.english), R.drawable.flag_us, "en"),
            LanguageItem(getString(R.string.french), R.drawable.flag_fr, "fr"),
            LanguageItem(getString(R.string.german), R.drawable.flag_de, "de"),
            LanguageItem(getString(R.string.japanese), R.drawable.flag_jp, "ja"),
            LanguageItem(getString(R.string.korean), R.drawable.flag_kr, "ko"),
            LanguageItem(getString(R.string.portuguese), R.drawable.flag_pt, "pt"),
            LanguageItem(getString(R.string.spanish), R.drawable.flag_es, "es"),
            LanguageItem(getString(R.string.arabic), R.drawable.flag_ar, "ar"),
        )

        mAdapter = LanguageAdapter(languages) { selected ->
            mBinding.titleLanguage.text = selected.name
            mSelectedLanguageItem = selected
            LanguageUtils.setLocale(this, mSelectedLanguageItem!!.code)
        }

        mBinding.languageRecyclerView.layoutManager = LinearLayoutManager(this)
        mBinding.languageRecyclerView.adapter = mAdapter
    }

    private fun setupListeners() {
        mBinding.backButton.setOnClickListener {
            finish()
        }
        mBinding.applyButton.setOnClickListener {
            Toast.makeText(
                this,
                getString(R.string.selected) + " " + (mSelectedLanguageItem?.name ?: ""),
                Toast.LENGTH_SHORT
            ).show()
            val resultIntent = Intent()
            resultIntent.putExtra(Constant.LANGUAGE, mSelectedLanguageItem?.code)
            setResult(RESULT_OK, resultIntent)
            SharePreferenceUtils.saveLanguage(this, mSelectedLanguageItem!!.code)
            finish()
        }
    }
}