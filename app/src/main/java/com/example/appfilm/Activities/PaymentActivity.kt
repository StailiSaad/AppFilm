package com.example.appfilm.Activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * PaymentActivity
 *
 * Activity responsible for handling VIP subscription payments.
 * The UI is implemented entirely using Jetpack Compose (no XML layouts).
 *
 * Features:
 * - VIP level selection
 * - Multiple payment methods (Visa, MasterCard, PayPal)
 * - Dynamic payment forms
 * - Basic input validation
 * - Simulated payment processing
 */
class PaymentActivity : ComponentActivity() {

    /**
     * Initializes the activity and sets the Compose content.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PaymentScreen { vipLevel, price, description, method, paypalEmail ->
                val intent = Intent(this, PaymentSuccessActivity::class.java)
                intent.putExtra("vip_level", vipLevel)
                intent.putExtra("vip_price", price)
                intent.putExtra("vip_description", description)
                intent.putExtra("payment_method", method)
                intent.putExtra("paypal_email", paypalEmail)
                startActivity(intent)
                finish()
            }
        }
    }
}

/**
 * Composable that represents the complete payment screen UI.
 *
 * @param onPaymentConfirmed Callback triggered when the payment is successfully confirmed.
 * It returns all necessary payment data to be passed to the next screen.
 */
@Composable
fun PaymentScreen(
    onPaymentConfirmed: (
        vipLevel: Int,
        price: Double,
        description: String,
        paymentMethod: String,
        paypalEmail: String
    ) -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current

    /** Selected VIP level (1, 2, or 3) */
    var selectedVIP by remember { mutableStateOf(0) }

    /** Selected payment method */
    var paymentMethod by remember { mutableStateOf("") }

    /** Card payment fields */
    var cardNumber by remember { mutableStateOf("") }
    var expDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    /** PayPal payment fields */
    var paypalEmail by remember { mutableStateOf("") }
    var paypalPassword by remember { mutableStateOf("") }

    /** Indicates whether payment is currently being processed */
    var processing by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            text = "Select VIP Level",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        VIPItem("VIP Level 1", "$9.99/month", selectedVIP == 1) { selectedVIP = 1 }
        VIPItem("VIP Level 2", "$19.99/month", selectedVIP == 2) { selectedVIP = 2 }
        VIPItem("VIP Level 3", "$29.99/month", selectedVIP == 3) { selectedVIP = 3 }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Select Payment Method",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        PaymentRadio("Visa", paymentMethod) { paymentMethod = "Visa" }
        PaymentRadio("MasterCard", paymentMethod) { paymentMethod = "MasterCard" }
        PaymentRadio("PayPal", paymentMethod) { paymentMethod = "PayPal" }

        Spacer(modifier = Modifier.height(16.dp))

        if (paymentMethod == "Visa" || paymentMethod == "MasterCard") {
            CardDetails(
                cardNumber = cardNumber,
                expDate = expDate,
                cvv = cvv,
                onCardChange = { cardNumber = it },
                onExpChange = { expDate = it },
                onCvvChange = { cvv = it }
            )
        }

        if (paymentMethod == "PayPal") {
            PayPalDetails(
                email = paypalEmail,
                password = paypalPassword,
                onEmailChange = { paypalEmail = it },
                onPasswordChange = { paypalPassword = it }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (selectedVIP == 0 || paymentMethod.isEmpty()) {
                    Toast.makeText(context, "Please complete all fields", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                processing = true
                Handler(Looper.getMainLooper()).postDelayed({
                    val (price, desc) = when (selectedVIP) {
                        1 -> 9.99 to "Basic VIP access"
                        2 -> 19.99 to "Premium VIP access"
                        3 -> 29.99 to "Ultimate VIP access"
                        else -> 0.0 to ""
                    }

                    onPaymentConfirmed(
                        selectedVIP,
                        price,
                        desc,
                        paymentMethod,
                        paypalEmail
                    )
                }, 1500)
            },
            enabled = !processing,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (processing) "Processing..." else "Confirm Payment")
        }
    }
}
