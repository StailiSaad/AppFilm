package com.example.appfilm.Activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.appfilm.R

/**
 * Activity for handling VIP subscription payments with multiple payment methods
 */
class PaymentActivity : Activity() {

    private lateinit var vipLevel1: LinearLayout
    private lateinit var vipLevel2: LinearLayout
    private lateinit var vipLevel3: LinearLayout
    private lateinit var vipLevelText: TextView
    private lateinit var paymentMethodGroup: RadioGroup
    private lateinit var paymentDetailsLayout: LinearLayout
    private lateinit var paypalDetailsLayout: LinearLayout
    private lateinit var cardNumberEditText: EditText
    private lateinit var expirationDateEditText: EditText
    private lateinit var cvvEditText: EditText
    private lateinit var paypalEmailEditText: EditText
    private lateinit var paypalPasswordEditText: EditText
    private lateinit var confirmPaymentButton: Button

    private var selectedVIPLevel = 0

    /**
     * Initializes the payment activity and sets up UI components
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        setupViews()
        setupVIPLevels()
        setupPaymentMethods()
        setupConfirmButton()

        paymentDetailsLayout.visibility = View.GONE
        paypalDetailsLayout.visibility = View.GONE
    }

    /**
     * Initializes all view references from the layout
     */
    private fun setupViews() {
        vipLevel1 = findViewById(R.id.vipLevel1)
        vipLevel2 = findViewById(R.id.vipLevel2)
        vipLevel3 = findViewById(R.id.vipLevel3)
        vipLevelText = findViewById(R.id.vipLevelText)
        paymentMethodGroup = findViewById(R.id.paymentMethodGroup)
        paymentDetailsLayout = findViewById(R.id.paymentDetailsLayout)
        paypalDetailsLayout = findViewById(R.id.paypalDetailsLayout)
        cardNumberEditText = findViewById(R.id.cardNumberEditText)
        expirationDateEditText = findViewById(R.id.expirationDateEditText)
        cvvEditText = findViewById(R.id.cvvEditText)
        paypalEmailEditText = findViewById(R.id.paypalEmailEditText)
        paypalPasswordEditText = findViewById(R.id.paypalPasswordEditText)
        confirmPaymentButton = findViewById(R.id.confirmPaymentButton)
    }

    /**
     * Sets up click listeners for VIP level selection
     */
    private fun setupVIPLevels() {
        vipLevel1.setOnClickListener { selectVIPLevel(1, "VIP Level 1 - $9.99/month") }
        vipLevel2.setOnClickListener { selectVIPLevel(2, "VIP Level 2 - $19.99/month") }
        vipLevel3.setOnClickListener { selectVIPLevel(3, "VIP Level 3 - $29.99/month") }
    }

    /**
     * Selects a VIP level and updates the UI accordingly
     */
    private fun selectVIPLevel(level: Int, levelText: String) {
        selectedVIPLevel = level
        vipLevelText.text = levelText

        resetVIPBackgrounds()

        val selectedColor = ContextCompat.getColor(this, android.R.color.holo_blue_dark)
        when (level) {
            1 -> vipLevel1.setBackgroundColor(selectedColor)
            2 -> vipLevel2.setBackgroundColor(selectedColor)
            3 -> vipLevel3.setBackgroundColor(selectedColor)
        }
    }

    /**
     * Resets all VIP level backgrounds to default color
     */
    private fun resetVIPBackgrounds() {
        val defaultColor = ContextCompat.getColor(this, android.R.color.darker_gray)
        vipLevel1.setBackgroundColor(defaultColor)
        vipLevel2.setBackgroundColor(defaultColor)
        vipLevel3.setBackgroundColor(defaultColor)
    }

    /**
     * Sets up payment method selection with dynamic form display
     */
    private fun setupPaymentMethods() {
        paymentMethodGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.visaRadio, R.id.mastercardRadio -> {
                    paymentDetailsLayout.visibility = View.VISIBLE
                    paypalDetailsLayout.visibility = View.GONE
                }
                R.id.paypalRadio -> {
                    paymentDetailsLayout.visibility = View.GONE
                    paypalDetailsLayout.visibility = View.VISIBLE
                }
                else -> {
                    paymentDetailsLayout.visibility = View.GONE
                    paypalDetailsLayout.visibility = View.GONE
                }
            }
        }
    }

    /**
     * Sets up the confirm payment button with validation and processing
     */
    private fun setupConfirmButton() {
        confirmPaymentButton.setOnClickListener {
            if (validateInput()) {
                confirmPaymentButton.isEnabled = false
                confirmPaymentButton.text = "Processing..."

                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this, PaymentSuccessActivity::class.java)

                    val (price, description) = when (selectedVIPLevel) {
                        1 -> Pair(9.99, "Basic VIP access with limited features")
                        2 -> Pair(19.99, "Premium VIP access with more features")
                        3 -> Pair(29.99, "Ultimate VIP access with all features")
                        else -> Pair(0.0, "No VIP level selected")
                    }

                    val paymentMethod = when (paymentMethodGroup.checkedRadioButtonId) {
                        R.id.visaRadio -> "Visa"
                        R.id.mastercardRadio -> "MasterCard"
                        R.id.paypalRadio -> "PayPal"
                        else -> "Unknown"
                    }

                    val paypalEmail = if (paymentMethodGroup.checkedRadioButtonId == R.id.paypalRadio) {
                        paypalEmailEditText.text.toString().trim()
                    } else {
                        ""
                    }

                    intent.putExtra("vip_level", selectedVIPLevel)
                    intent.putExtra("vip_price", price)
                    intent.putExtra("vip_description", description)
                    intent.putExtra("payment_method", paymentMethod)
                    intent.putExtra("paypal_email", paypalEmail)
                    intent.putExtra("save_payment", false)

                    startActivity(intent)
                    finish()
                }, 1500)
            }
        }
    }

    /**
     * Validates all input fields for payment processing
     */
    private fun validateInput(): Boolean {
        if (selectedVIPLevel == 0) {
            Toast.makeText(this, "Please select a VIP level", Toast.LENGTH_SHORT).show()
            return false
        }

        val checkedId = paymentMethodGroup.checkedRadioButtonId
        if (checkedId == -1) {
            Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show()
            return false
        }

        if (checkedId == R.id.visaRadio || checkedId == R.id.mastercardRadio) {
            val cardNumber = cardNumberEditText.text.toString().trim()
            val expDate = expirationDateEditText.text.toString().trim()
            val cvv = cvvEditText.text.toString().trim()

            if (cardNumber.isEmpty()) {
                cardNumberEditText.error = "Enter card number"
                return false
            } else if (cardNumber.length != 16) {
                cardNumberEditText.error = "Enter 16-digit card number"
                return false
            }

            if (expDate.isEmpty()) {
                expirationDateEditText.error = "Enter expiration date"
                return false
            } else if (!expDate.matches(Regex("\\d{2}\\d{2}"))) {
                expirationDateEditText.error = "Use MM/YY format"
                return false
            }

            if (cvv.isEmpty()) {
                cvvEditText.error = "Enter CVV"
                return false
            } else if (cvv.length != 3) {
                cvvEditText.error = "Enter 3-digit CVV"
                return false
            }
        }

        if (checkedId == R.id.paypalRadio) {
            val paypalEmail = paypalEmailEditText.text.toString().trim()
            val paypalPassword = paypalPasswordEditText.text.toString().trim()

            if (paypalEmail.isEmpty()) {
                paypalEmailEditText.error = "Enter PayPal email"
                return false
            } else if (!isValidEmail(paypalEmail)) {
                paypalEmailEditText.error = "Enter valid email address"
                return false
            }

            if (paypalPassword.isEmpty()) {
                paypalPasswordEditText.error = "Enter PayPal password"
                return false
            } else if (paypalPassword.length < 6) {
                paypalPasswordEditText.error = "Password must be at least 6 characters"
                return false
            }
        }

        return true
    }

    /**
     * Validates email format using regex pattern
     */
    private fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")
        return email.matches(emailRegex)
    }
}