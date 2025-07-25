package com.example.navigationdrawer

import android.content.Context
import java.util.Locale


class LanguageUtils {
    companion object {
        //new
//        fun getContextWithLocale(context: Context, languageCode: String): Context {
//            val locale = Locale(languageCode)
//            val config = context.resources.configuration
//            config.setLocale(locale)
//            return context.createConfigurationContext(config)
//        }

        //old
        fun setLocale(context: Context, languageCode: String) {
            val res = context.resources
            val dm = res.displayMetrics
            val conf = res.configuration
            conf.setLocale(Locale(languageCode.lowercase(Locale.getDefault()))) // API 17+ only.
            res.updateConfiguration(conf, dm)
        }
    }
}