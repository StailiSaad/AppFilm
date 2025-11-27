package com.example.appfilm.Activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.appfilm.R

/**
 * Activity that displays payment confirmation details after successful VIP subscription
 */
class PaymentSuccessActivity : Activity() {

    private lateinit var successTitle: TextView
    private lateinit var vipLevelText: TextView
    private lateinit var priceText: TextView
    private lateinit var descriptionText: TextView
    private lateinit var paymentMethodText: TextView
    private lateinit var paypalEmailText: TextView
    private lateinit var savePaymentText: TextView
    private lateinit var backToHomeButton: Button

    /**
     * Initializes the success activity and displays payment details
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_success)

        setupViews()
        displayPaymentDetails()
        setupButton()
    }

    /**
     * Initializes all view references from the layout
     */
    private fun setupViews() {
        successTitle = findViewById(R.id.successTitle)
        vipLevelText = findViewById(R.id.vipLevelText)
        priceText = findViewById(R.id.priceText)
        descriptionText = findViewById(R.id.descriptionText)
        paymentMethodText = findViewById(R.id.paymentMethodText)
        paypalEmailText = findViewById(R.id.paypalEmailText)
        savePaymentText = findViewById(R.id.savePaymentText)
        backToHomeButton = findViewById(R.id.backToHomeButton)
    }

    /**
     * Displays the payment details received from the PaymentActivity
     */
    private fun displayPaymentDetails() {
        val vipLevel = intent.getIntExtra("vip_level", 0)
        val vipPrice = intent.getDoubleExtra("vip_price", 0.0)
        val vipDescription = intent.getStringExtra("vip_description") ?: "No description available"
        val paymentMethod = intent.getStringExtra("payment_method") ?: "Unknown"
        val paypalEmail = intent.getStringExtra("paypal_email") ?: ""
        val savePayment = intent.getBooleanExtra("save_payment", false)

        vipLevelText.text = "VIP Level: $vipLevel"
        priceText.text = "Price: $${"%.2f".format(vipPrice)}/month"
        descriptionText.text = vipDescription
        paymentMethodText.text = "Payment Method: $paymentMethod"

        // Show PayPal email only if PayPal was used
        if (paymentMethod == "PayPal" && paypalEmail.isNotEmpty()) {
            paypalEmailText.text = "PayPal Email: $paypalEmail"
            paypalEmailText.visibility = View.VISIBLE
        } else {
            paypalEmailText.visibility = View.GONE
        }

        savePaymentText.text = if (savePayment) "Payment method saved for future use" else "Payment method not saved"
    }

    /**
     * Sets up the back to home button with navigation
     */
    private fun setupButton() {
        backToHomeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
    }
}