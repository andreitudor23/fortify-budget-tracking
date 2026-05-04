package com.echipappa.fortify.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
fun SignUpScreen(
    onSignUp: () -> Unit = {},
    onSignIn: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var acceptedTerms by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NavyDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            // Back button
            TextButton(
                onClick = onBack,
                contentPadding = PaddingValues(0.dp)
            ) {
                Text("← Back", color = TextSecondary, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Logo
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(ElectricBlue)
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                Text("🛡", fontSize = 28.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Create Account",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Join FORTIFY and take control",
                fontSize = 16.sp,
                color = TextSecondary,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Full Name
            Text("Full Name", fontSize = 14.sp, color = TextSecondary, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                placeholder = { Text("John Doe", color = TextMuted) },
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
                leadingIcon = { Text("👤", fontSize = 18.sp) },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email
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
                leadingIcon = { Text("✉", fontSize = 18.sp) },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password
            Text("Password", fontSize = 14.sp, color = TextSecondary, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Min. 8 characters", color = TextMuted) },
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
                leadingIcon = { Text("🔒", fontSize = 18.sp) },
                trailingIcon = {
                    TextButton(onClick = { passwordVisible = !passwordVisible }) {
                        Text(if (passwordVisible) "👁" else "👁", fontSize = 18.sp)
                    }
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Confirm Password
            Text("Confirm Password", fontSize = 14.sp, color = TextSecondary, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = { Text("Repeat your password", color = TextMuted) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (confirmPassword.isNotEmpty() && confirmPassword != password) DangerRed else ElectricBlue,
                    unfocusedBorderColor = if (confirmPassword.isNotEmpty() && confirmPassword != password) DangerRed else NavyCardLight,
                    focusedContainerColor = NavyCard,
                    unfocusedContainerColor = NavyCard,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                leadingIcon = { Text("🔒", fontSize = 18.sp) },
                trailingIcon = {
                    TextButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Text(if (confirmPasswordVisible) "👁" else "👁", fontSize = 18.sp)
                    }
                },
                singleLine = true
            )

            if (confirmPassword.isNotEmpty() && confirmPassword != password) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Passwords don't match",
                    fontSize = 12.sp,
                    color = DangerRed
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Terms
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = acceptedTerms,
                    onCheckedChange = { acceptedTerms = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = ElectricBlue,
                        uncheckedColor = TextSecondary
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "I agree to the ",
                    fontSize = 14.sp,
                    color = TextSecondary
                )
                Text(
                    "Terms & Privacy Policy",
                    fontSize = 14.sp,
                    color = ElectricBlue,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Sign Up button
            Button(
                onClick = onSignUp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (acceptedTerms) ElectricBlue else NavyCardLight
                ),
                shape = RoundedCornerShape(16.dp),
                enabled = acceptedTerms
            ) {
                Text(
                    "Create Account",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Sign In link
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Already have an account? ", fontSize = 14.sp, color = TextSecondary)
                TextButton(
                    onClick = onSignIn,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        "Sign In",
                        fontSize = 14.sp,
                        color = ElectricBlue,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    FortifyTheme {
        SignUpScreen()
    }
}