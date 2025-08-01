package com.example.navigationdrawer.screen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.GravityCompat
import androidx.navigation.ui.AppBarConfiguration
import com.bac.simplecalculator.R
import com.bac.simplecalculator.databinding.ActivityMainBinding
import com.example.navigationdrawer.BaseActivity
import com.example.navigationdrawer.Constant
import com.example.navigationdrawer.Constants
import com.example.navigationdrawer.LanguageNewActivity
import com.example.navigationdrawer.LanguageUtils
import com.example.navigationdrawer.MySharedPreferences
import com.example.navigationdrawer.SharePreferenceUtils
import com.example.navigationdrawer.ShopActivity
import com.example.navigationdrawer.ThemeUtils
import com.example.navigationdrawer.fullStatusBar
import com.google.android.material.navigation.NavigationView

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private var operator = ""
    private var value1 = 0.0
    private var value2 = 0.0
    private var result = 0.0
    private lateinit var languageLauncher: ActivityResultLauncher<Intent>
    private lateinit var themeLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        fullStatusBar()
        ThemeUtils.setAppTheme(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNumberListeners()
        setOperatorListeners()
        languageLauncherResult()
        themeLauncherResult()
        initListeners()

    }

    private fun initListeners() {
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val headerView = navigationView.getHeaderView(0)
        val ll1 = headerView.findViewById<LinearLayout>(R.id.ll1)
        val ll2 = headerView.findViewById<LinearLayout>(R.id.ll2)
        val ll3 = headerView.findViewById<LinearLayout>(R.id.ll3)

        ll1.setOnClickListener {
            startActivity(Intent(this, LanguageNewActivity::class.java))
        }

        ll2.setOnClickListener {
            startActivity(Intent(this, ThemeActivity::class.java))
        }

        ll3.setOnClickListener {
            startActivity(Intent(this, RateActivity::class.java))
        }

        binding.tvGem.setOnClickListener {
            startActivity(Intent(this, ShopActivity::class.java))
        }

        binding.btnDecimal.setOnClickListener { handleDecimal() }
        binding.btnClear.setOnClickListener { binding.Output.text = ""; operator = "" }
        binding.btnDel.setOnClickListener { binding.Output.text = binding.Output.text.dropLast(1) }
        binding.btnResult.setOnClickListener { buttonResult() }
        binding.menu.setOnClickListener { binding.drawerLayout.openDrawer(GravityCompat.START) }
    }

    private fun themeLauncherResult() {
        themeLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val data: Intent? = result.data
                    val theme = data?.getStringExtra(Constant.THEME)
                    Log.i("dongdong", "set theme: $theme")
                    ThemeUtils.setAppTheme(this)
                    recreate()
                }
            }
    }

    private fun languageLauncherResult() {
        languageLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val data: Intent? = result.data
                    val languageCode = data?.getStringExtra(Constant.LANGUAGE)
                    Log.i("dongdong", "set language: $languageCode")
                    recreate()
                }
            }
    }

    private fun setLanguage() {
        val langCode = SharePreferenceUtils.getLanguage(this) ?: "en"
        LanguageUtils.setLocale(this, langCode)
    }

    private fun setNumberListeners() {
        val buttons = listOf(
            binding.btn0, binding.btn1, binding.btn2, binding.btn3, binding.btn4,
            binding.btn5, binding.btn6, binding.btn7, binding.btn8, binding.btn9
        )

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                binding.Output.append(index.toString())
            }
        }
    }

    private fun setOperatorListeners() {
        val ops = mapOf(
            binding.btnAdd to "+", binding.btnMinus to "-",
            binding.btnMul to "*", binding.btnDiv to "/",
            binding.btnPercentage to "%"
        )

        ops.forEach { (button, symbol) ->
            button.setOnClickListener { buttonOperation(symbol) }
        }
    }

    private fun handleDecimal() {
        val value = binding.Output.text.toString()

        if (value.isEmpty() || "+-*/%".contains(value.last())) return

        val lastPart = if (operator.isNotEmpty()) value.substringAfterLast(operator) else value
        if (!lastPart.contains(".")) {
            binding.Output.append(".")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun buttonOperation(op: String) {
        var value = binding.Output.text.toString()

        if (value.isEmpty()) return
        if ("+-*/%".contains(value.last())) {
            binding.Output.text = value.dropLast(1) + op
            operator = op
            return
        }

        if (operator.isNotEmpty() && value.contains(operator)) {
            val parts = value.split(operator)
            if (parts.size == 2 && parts[1].isNotEmpty()) {
                try {
                    value1 = parts[0].toDouble()
                    value2 = parts[1].toDouble()
                    result = calculate(value1, value2, operator) ?: return
                    val resText = result.toString().removeSuffix(".0")
                    binding.Output.text = "$resText$op"
                    operator = op
                    return
                } catch (e: Exception) {
                    showToast(getString(R.string.error_format))
                    return
                }
            }
        }

        operator = op
        binding.Output.append(op)
    }

    private fun buttonResult(): String {
        val value = binding.Output.text.toString()
        if (!operator.isEmpty() && value.contains(operator)) {
            val parts = value.split(operator)
            if (parts.size == 2) {
                value1 = parts[0].toDoubleOrNull() ?: return ""
                value2 = parts[1].toDoubleOrNull() ?: return ""

                result = calculate(value1, value2, operator) ?: return ""
                binding.Output.text = result.toString()
            }
        }
        return result.toString()
    }

    private fun calculate(a: Double, b: Double, op: String): Double? {
        return when (op) {
            "+" -> a + b
            "-" -> a - b
            "*" -> a * b
            "/" -> if (b != 0.0) a / b else {
                showToast(getString(R.string.can_not_divide_by_zero))
                null
            }

            "%" -> a % b
            else -> null
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        binding.gold.text = MySharedPreferences.getInstance().getIntData(Constants.SAVE_GOLD).toString()
    }
}
