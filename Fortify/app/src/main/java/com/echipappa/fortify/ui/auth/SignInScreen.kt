package com.echipappa.fortify.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.echipappa.fortify.ui.theme.*

@Composable
fun SignInScreen(
    onSignIn: (String, String) -> Unit = { _, _ -> },
    onBack: () -> Unit = {},
    errorMessage: String = ""
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NavyDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            TextButton(
                onClick = onBack,
                contentPadding = PaddingValues(0.dp)
            ) {
                Text("← Back", color = TextSecondary, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(ElectricBlue)
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "F",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Welcome Back",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Sign in to your account",
                fontSize = 16.sp,
                color = TextSecondary,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text("Email", fontSize = 14.sp, color = TextSecondary, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("you@example.com", color = TextMuted) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ElectricBlue,
                    unfocusedBorderColor = NavyCardLight,
                    focusedContainerColor = NavyCard,
                    unfocusedContainerColor = NavyCard,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Password", fontSize = 14.sp, color = TextSecondary, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("••••••••", color = TextMuted) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ElectricBlue,
                    unfocusedBorderColor = NavyCardLight,
                    focusedContainerColor = NavyCard,
                    unfocusedContainerColor = NavyCard,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                trailingIcon = {
                    TextButton(onClick = { passwordVisible = !passwordVisible }) {
                        Text(
                            if (passwordVisible) "Hide" else "Show",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (errorMessage.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF2A0A0A))
                        .padding(12.dp)
                ) {
                    Text(
                        text = errorMessage,
                        fontSize = 14.sp,
                        color = DangerRed,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            Button(
                onClick = { onSignIn(email, password) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Sign In", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    FortifyTheme {
        SignInScreen()
    }
}
