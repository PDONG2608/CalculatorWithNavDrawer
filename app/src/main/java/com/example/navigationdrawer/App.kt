package com.example.navigationdrawer

import android.app.Application
import com.example.navigationdrawer.MySharedPreferences.Companion.initializeInstance

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeInstance(this)
    }
}