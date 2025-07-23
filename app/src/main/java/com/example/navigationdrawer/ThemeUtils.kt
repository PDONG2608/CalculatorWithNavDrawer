package com.example.navigationdrawer

import android.app.Activity
import com.bac.simplecalculator.R

class ThemeUtils {
    companion object {
        fun setAppTheme(activity: Activity) {
            when (SharePreferenceUtils.getTheme(activity)) {
                "brown" -> activity.setTheme(R.style.Theme_Brown)
                "purple" -> activity.setTheme(R.style.Theme_Purple)
                "green" -> activity.setTheme(R.style.Theme_Green)
                "blue" -> activity.setTheme(R.style.Theme_Blue)
                "yellow" -> activity.setTheme(R.style.Theme_Yellow)
                "red" -> activity.setTheme(R.style.Theme_Red)
                "pink" -> activity.setTheme(R.style.Theme_Pink)
                "orange" -> activity.setTheme(R.style.Theme_Orange)
                else -> activity.setTheme(R.style.Theme_Green) // default fallback
            }
        }
    }
}