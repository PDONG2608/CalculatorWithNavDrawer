package com.example.navigationdrawer.screen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.navigationdrawer.Constant
import com.example.navigationdrawer.LanguageUtils
import com.example.navigationdrawer.R
import com.example.navigationdrawer.SharePreferenceUtils
import com.example.navigationdrawer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    private var operator = ""
    private var value1 = 0.0
    private var value2 = 0.0
    private var result = 0.0
    private lateinit var languageLauncher: ActivityResultLauncher<Intent>
    private lateinit var themeLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        setLanguage()
        setAppTheme()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_language -> {
                    val intent = Intent(this, LanguageActivity::class.java)
                    languageLauncher.launch(intent)
                }
                R.id.nav_change_theme -> {
                    val intent = Intent(this, ThemeActivity::class.java)
                    themeLauncher.launch(intent)
                }
                R.id.nav_rate_us -> startActivity(Intent(this, RateActivity::class.java))
            }
            true
        }

        setNumberListeners()
        setOperatorListeners()

        binding.btnDecimal.setOnClickListener { handleDecimal() }
        binding.btnClear.setOnClickListener { binding.Output.text = ""; operator = "" }
        binding.btnDel.setOnClickListener { binding.Output.text = binding.Output.text.dropLast(1) }
        binding.btnResult.setOnClickListener { buttonResult() }

        languageLauncherResult()
        themeLauncherResult()
    }

    private fun themeLauncherResult() {
        themeLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val theme = data?.getStringExtra(Constant.THEME)
                Log.i("dongdong", "set theme: $theme")
                setAppTheme()
                recreate()
            }
        }
    }

    private fun languageLauncherResult() {
        languageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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

    private fun setAppTheme() {
        when (SharePreferenceUtils.getTheme(this)) {
            "black" -> setTheme(R.style.Theme_Black)
            "purple" -> setTheme(R.style.Theme_Purple)
            "green" -> setTheme(R.style.Theme_Green)
            "blue" -> setTheme(R.style.Theme_Blue)
            "yellow" -> setTheme(R.style.Theme_Yellow)
            "red" -> setTheme(R.style.Theme_Red)
            "pink" -> setTheme(R.style.Theme_Pink)
            "orange" -> setTheme(R.style.Theme_Orange)
            else -> setTheme(R.style.Theme_Green) // default fallback
        }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return toggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item)
    }
}
