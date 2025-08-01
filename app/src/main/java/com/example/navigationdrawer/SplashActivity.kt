package com.example.navigationdrawer

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bac.simplecalculator.R
import com.bac.simplecalculator.databinding.ActivityShopBinding
import com.bac.simplecalculator.databinding.ActivitySplashBinding
import com.example.navigationdrawer.screen.MainActivity

class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val coins: MutableList<Int> = mutableListOf(
        0,
        0,
        100,
        100,
        200,
        250,
        350,
        400,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!MySharedPreferences.getInstance().getBooleanPreference("key", false)) {
            MySharedPreferences.getInstance().saveListInt(coins, Constants.SAVE_LIST_COINS, this)
            MySharedPreferences.getInstance().saveData("key", true)
        }

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
        }, 3000)
    }
}