package com.example.appfilm.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
 * LoginActivity
 *
 * Activity responsible for user authentication.
 * The user logs in using an email and password.
 *
 * The UI is fully implemented using Jetpack Compose
 * without any XML layout files.
 */
class LoginActivity : ComponentActivity() {

    /**
     * Initializes the activity and sets the Compose UI content.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LoginScreen(
                onLoginSuccess = {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            )
        }
    }
}

/**
 * Composable that displays the login screen.
 *
 * @param onLoginSuccess Callback invoked when the user
 * successfully enters valid login credentials.
 */
@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {

    /** Email entered by the user */
    var email by remember { mutableStateOf("") }

    /** Password entered by the user */
    var password by remember { mutableStateOf("") }

    /** Context used for showing Toast messages */
    val context = androidx.compose.ui.platform.LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F0F0F))
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Film App Login",
            color = Color(0xFF03A9F4),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 50.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        )

        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    Toast.makeText(
                        context,
                        "Please enter email and password",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    onLoginSuccess()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF03A9F4)
            )
        ) {
            Text(
                text = "LOGIN",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
