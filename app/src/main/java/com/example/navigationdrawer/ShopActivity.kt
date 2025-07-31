package com.example.navigationdrawer

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.MutableLiveData
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ConsumeParams
import com.android.billingclient.api.PendingPurchasesParams
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import com.bac.simplecalculator.R
import com.bac.simplecalculator.databinding.ActivityLanguageBinding
import com.bac.simplecalculator.databinding.ActivityShopBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

private const val KEY_IN_APP1 = "key_in_app1"
private const val KEY_IN_APP2 = "key_in_app2"
private const val KEY_IN_APP3 = "key_in_app3"
private const val KEY_IN_APP4 = "key_in_app4"

class ShopActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShopBinding
    private lateinit var billingClient: BillingClient
    private var coins = arrayListOf(50, 150, 250, 500)
    private var inAppProductIds = arrayListOf(KEY_IN_APP1, KEY_IN_APP2, KEY_IN_APP3, KEY_IN_APP4)
    private val optionSelectLiveData = MutableLiveData(KEY_IN_APP1)
    private var keyBuy = inAppProductIds[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fullStatusBar()
        binding.gold.text = MySharedPreferences.getInstance().getIntData(Constants.SAVE_GOLD).toString()
        initBilling()
        initListener()
    }

    private fun initBilling() {
        // Khởi tạo
        initBillingClientAndLoadPrices(this) { prices ->
            runOnUiThread {
                binding.priceInApp1.text = "${prices[KEY_IN_APP1]}"
                binding.priceInApp2.text = "${prices[KEY_IN_APP2]}"
                binding.priceInApp3.text = "${prices[KEY_IN_APP3]}"
                binding.priceInApp4.text = "${prices[KEY_IN_APP4]}"
            }
        }
    }

    private fun initBillingClientAndLoadPrices(
        context: Context,
        onPricesLoaded: (Map<String, String>) -> Unit
    ) {
        val params = PendingPurchasesParams.newBuilder()
            .enableOneTimeProducts()
            .build()

        billingClient = BillingClient.newBuilder(context)
            .enablePendingPurchases(params)
            .setListener(purchasesListener)
            .build()

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingServiceDisconnected() {}

            @SuppressLint("SetTextI18n")
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode != BillingClient.BillingResponseCode.OK) return

                val resultMap = mutableMapOf<String, String>()

                val inAppParams = QueryProductDetailsParams.newBuilder()
                    .setProductList(inAppProductIds.map {
                        QueryProductDetailsParams.Product.newBuilder()
                            .setProductId(it)
                            .setProductType(BillingClient.ProductType.INAPP)
                            .build()
                    }).build()

                billingClient.queryProductDetailsAsync(inAppParams) { billingResult, productDetailsResult ->
                    if (billingResult.responseCode != BillingClient.BillingResponseCode.OK) return@queryProductDetailsAsync
                    val productDetailsList = productDetailsResult.productDetailsList
                    productDetailsList.forEach {
                        val price = it.oneTimePurchaseOfferDetails?.formattedPrice ?: "N/A"
                        resultMap[it.productId] = price
                    }
                    onPricesLoaded(resultMap)
                }
            }
        })
    }

    private val purchasesListener = PurchasesUpdatedListener { billingResult, purchases ->
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (purchase in purchases) {
                if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
                    // ✅ Gửi xác nhận nếu cần, rồi acknowledge
                    acknowledgePurchase(purchase)
                }
            }
        }
    }

    private fun acknowledgePurchase(purchase: Purchase) {
        if (purchase.isAcknowledged) return

        val params = AcknowledgePurchaseParams.newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()

        billingClient.acknowledgePurchase(params) { billingResult ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                giveUserCoins(purchase)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun giveUserCoins(purchase: Purchase?) {
        var boughtCoins: Int
        inAppProductIds.forEachIndexed { index, productId ->
            if (purchase!!.products[0] == productId) {
                boughtCoins = coins[index] * purchase.quantity
                var totalGem = MySharedPreferences.getInstance().getIntData(Constants.SAVE_GOLD) + boughtCoins
                MySharedPreferences.getInstance().saveData(Constants.SAVE_GOLD, totalGem)
                MainScope().launch {
                    binding.gold.text = MySharedPreferences.getInstance().getIntData(Constants.SAVE_GOLD).toString()
                }
            }
        }
    }

    private fun initListener() {
        binding.apply {
            icBack.setOnClickListener { finish() }
            constrainInApp1.setOnClickListener { optionSelectLiveData.postValue(KEY_IN_APP1) }
            constrainInApp2.setOnClickListener { optionSelectLiveData.postValue(KEY_IN_APP2) }
            constrainInApp3.setOnClickListener { optionSelectLiveData.postValue(KEY_IN_APP3) }
            constrainInApp4.setOnClickListener { optionSelectLiveData.postValue(KEY_IN_APP4) }

            btnPurchase.setOnClickListener {
                launchPurchase(this@ShopActivity, keyBuy)
            }
        }

        optionSelectLiveData.observe(this) {
            when (it) {
                KEY_IN_APP1 -> {
                    binding.apply {
                        constrainInApp1.setBackgroundResource(R.drawable.bg_sub_selected)
                        constrainInApp2.setBackgroundResource(R.drawable.bg_sub_normal)
                        constrainInApp3.setBackgroundResource(R.drawable.bg_sub_normal)
                        constrainInApp4.setBackgroundResource(R.drawable.bg_sub_normal)
                    }
                    keyBuy = inAppProductIds[0]
                }

                KEY_IN_APP2 -> {
                    binding.apply {
                        constrainInApp1.setBackgroundResource(R.drawable.bg_sub_normal)
                        constrainInApp2.setBackgroundResource(R.drawable.bg_sub_selected)
                        constrainInApp3.setBackgroundResource(R.drawable.bg_sub_normal)
                        constrainInApp4.setBackgroundResource(R.drawable.bg_sub_normal)
                    }
                    keyBuy = inAppProductIds[1]
                }

                KEY_IN_APP3 -> {
                    binding.apply {
                        constrainInApp1.setBackgroundResource(R.drawable.bg_sub_normal)
                        constrainInApp2.setBackgroundResource(R.drawable.bg_sub_normal)
                        constrainInApp3.setBackgroundResource(R.drawable.bg_sub_selected)
                        constrainInApp4.setBackgroundResource(R.drawable.bg_sub_normal)
                    }
                    keyBuy = inAppProductIds[2]
                }

                KEY_IN_APP4 -> {
                    binding.apply {
                        constrainInApp1.setBackgroundResource(R.drawable.bg_sub_normal)
                        constrainInApp2.setBackgroundResource(R.drawable.bg_sub_normal)
                        constrainInApp3.setBackgroundResource(R.drawable.bg_sub_normal)
                        constrainInApp4.setBackgroundResource(R.drawable.bg_sub_selected)
                    }
                    keyBuy = inAppProductIds[3]
                }
            }
        }
    }

    private fun launchPurchase(activity: Activity, productId: String) {
        val productType = when {
            inAppProductIds.contains(productId) -> BillingClient.ProductType.INAPP
            else -> return
        }

        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(
                listOf(
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(productId)
                        .setProductType(productType)
                        .build()
                )
            ).build()

        billingClient.queryProductDetailsAsync(params) { billingResult, productDetailsResult ->
            if (billingResult.responseCode != BillingClient.BillingResponseCode.OK) return@queryProductDetailsAsync

            val productDetails = productDetailsResult.productDetailsList.firstOrNull() ?: return@queryProductDetailsAsync

            val offerToken = when (productType) {
                BillingClient.ProductType.INAPP -> null
                else -> null
            }

            val productDetailsParams = BillingFlowParams.ProductDetailsParams.newBuilder()
                .setProductDetails(productDetails)
            offerToken?.let { productDetailsParams.setOfferToken(it) }

            val flowParams = BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(listOf(productDetailsParams.build()))
                .build()

            billingClient.launchBillingFlow(activity, flowParams)
        }
    }
}