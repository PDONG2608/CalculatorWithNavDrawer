package com.example.navigationdrawer

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.navigationdrawer.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mToggle : ActionBarDrawerToggle
    private var mOperator: String = ""
    private var mValue1: Double = 0.0
    private var mValue2: Double = 0.0
    private var mResult: Double = 0.0
    private var numberclk = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val drawerLayout: DrawerLayout = mBinding.drawerLayout
        val navView : NavigationView = mBinding.navView

        mToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open,R.string.close)
        drawerLayout.addDrawerListener(mToggle)
        mToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            handleClickNavigationItem(it)
            true
        }

        mBinding.btn0.setOnClickListener { buttonClick(mBinding.btn0) }
        mBinding.btn1.setOnClickListener { buttonClick(mBinding.btn1) }
        mBinding.btn2.setOnClickListener { buttonClick(mBinding.btn2) }
        mBinding.btn3.setOnClickListener { buttonClick(mBinding.btn3) }
        mBinding.btn4.setOnClickListener { buttonClick(mBinding.btn4) }
        mBinding.btn5.setOnClickListener { buttonClick(mBinding.btn5) }
        mBinding.btn6.setOnClickListener { buttonClick(mBinding.btn6) }
        mBinding.btn7.setOnClickListener { buttonClick(mBinding.btn7) }
        mBinding.btn8.setOnClickListener { buttonClick(mBinding.btn8) }
        mBinding.btn9.setOnClickListener { buttonClick(mBinding.btn9) }
        mBinding.btnDecimal.setOnClickListener { buttonClick(mBinding.btnDecimal) }

        mBinding.btnClear.setOnClickListener { buttonClick(mBinding.btnClear) }
        mBinding.btnDel.setOnClickListener { buttonClick(mBinding.btnDel) }

        mBinding.btnAdd.setOnClickListener { buttonOperation(mBinding.btnAdd) }
        mBinding.btnMinus.setOnClickListener { buttonOperation(mBinding.btnMinus) }
        mBinding.btnPercentage.setOnClickListener { buttonOperation(mBinding.btnPercentage) }
        mBinding.btnMul.setOnClickListener { buttonOperation(mBinding.btnMul) }
        mBinding.btnDiv.setOnClickListener { buttonOperation(mBinding.btnDiv) }

        mBinding.btnResult.setOnClickListener { buttonResult() }
    }

    private fun buttonClick(visible: View) {

        numberclk = true
        var value = mBinding.Output.text.toString()

        when (visible.id) {

            mBinding.btn0.id -> {
                value += "0"
            }
            mBinding.btn1.id -> {
                value += "1"
            }
            mBinding.btn2.id -> {
                value += "2"
            }
            mBinding.btn3.id -> {
                value += "3"
            }
            mBinding.btn4.id -> {
                value += "4"
            }
            mBinding.btn5.id -> {
                value += "5"
            }
            mBinding.btn6.id -> {
                value += "6"
            }
            mBinding.btn7.id -> {
                value += "7"
            }
            mBinding.btn8.id -> {
                value += "8"
            }
            mBinding.btn9.id -> {
                value += "9"
            }
            mBinding.btnDecimal.id -> {
                value += "."
            }
            mBinding.btnClear.id -> {
                value = ""
            }
            mBinding.btnDel.id -> {
                value = mBinding.Output.text.toString()
                if (value.isNotEmpty()) {
                    value = value.substring(0, value.length - 1)
                }
            }

        }
        mBinding.Output.setText(value)
    }

    private fun buttonOperation(visible: View) {

        when (visible.id) {
            mBinding.btnAdd.id -> {
                mOperator = "+"
            }
            mBinding.btnMinus.id -> {
                mOperator = "-"
            }
            mBinding.btnDiv.id -> {
                mOperator = "/"
            }
            mBinding.btnPercentage.id -> {
                mOperator = "%"
            }
            mBinding.btnMul.id -> {
                mOperator = "*"
            }
        }

        val value = mBinding.Output.text.toString()
        if (numberclk) {
            mValue1 = value.toDouble()
        }
        numberclk = false
        mBinding.Output.setText("")

    }

    private fun buttonResult() {

        val value = mBinding.Output.text.toString()
        mValue2 = value.toDouble()

        when (mOperator) {

            "+" -> {
                mResult = mValue1 + mValue2
            }
            "-" -> {
                mResult = mValue1 - mValue2
            }
            "/" -> {
                mResult = mValue1 / mValue2
            }
            "%" -> {
                mResult = mValue1 % mValue2
            }
            "*" -> {
                mResult = mValue1 * mValue2
            }
        }
        mBinding.Output.text = mResult.toString()
    }

    private fun handleClickNavigationItem(it: MenuItem) {
        when(it.itemId){
            R.id.nav_language -> {
                startActivity(Intent(this, LanguageActivity::class.java))
            }
            R.id.nav_change_theme -> Toast.makeText(applicationContext, "Click Change Theme",Toast.LENGTH_SHORT).show()
            R.id.nav_rate_us -> {
                startActivity(Intent(this, RateActivity::class.java))
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item)
    }
}