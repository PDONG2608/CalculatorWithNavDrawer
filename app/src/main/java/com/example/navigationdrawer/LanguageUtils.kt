package com.example.navigationdrawer

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.example.navigationdrawer.BaseActivity.Companion.dLocale
import com.example.navigationdrawer.screen.MainActivity
import java.util.Locale


object LanguageUtils {
    fun setLocale(context: Activity, languageCode: String) {
        dLocale = Locale(languageCode)
        val refresh = Intent(context, MainActivity::class.java)
        context.startActivity(refresh)
        context.finishAffinity()
    }
}