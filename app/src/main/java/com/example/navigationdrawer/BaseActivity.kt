package com.example.navigationdrawer

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.view.ContextThemeWrapper
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

abstract class BaseActivity : AppCompatActivity() {

    companion object {
        var dLocale: Locale? = null
    }

    override fun onResume() {
        super.onResume()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        fullStatusBar()
    }

    init {
        val languageCode = MySharedPreferences.getInstance().getStringData(Constants.LANGUAGE_CURRENT_CODE, Locale.getDefault().language)
        dLocale = Locale(languageCode!!)
        updateConfig(this)
    }

    private fun updateConfig(wrapper: ContextThemeWrapper) {
        if (dLocale == Locale("")) // Do nothing if dLocale is null
            return

        Locale.setDefault(dLocale!!)
        val configuration = Configuration()
        configuration.setLocale(dLocale)
        wrapper.applyOverrideConfiguration(configuration)
    }
}