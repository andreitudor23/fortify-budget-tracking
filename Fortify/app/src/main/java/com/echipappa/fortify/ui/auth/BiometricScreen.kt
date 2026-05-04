package com.echipappa.fortify.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.echipappa.fortify.ui.theme.*

@Composable
fun BiometricScreen(
    onEnableBiometric: () -> Unit = {},
    onMaybeLater: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NavyDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            // Logo
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(ElectricBlue),
                contentAlignment = Alignment.Center
            ) {
                Text("🛡", fontSize = 28.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Secure Your Account",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Enable biometric authentication for faster, secure access",
                fontSize = 16.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Fingerprint icon
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(NavyCard),
                contentAlignment = Alignment.Center
            ) {
                Text("🔒", fontSize = 56.sp)
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Feature list
            BiometricFeatureItem(
                icon = "⚡",
                title = "Quick Access",
                description = "Sign in instantly with just a glance or touch"
            )
            Spacer(modifier = Modifier.height(12.dp))
            BiometricFeatureItem(
                icon = "🔐",
                title = "More Secure",
                description = "Your biometric data never leaves your device"
            )
            Spacer(modifier = Modifier.height(12.dp))
            BiometricFeatureItem(
                icon = "✨",
                title = "Seamless Experience",
                description = "No more remembering complex passwords"
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Enable button
            Button(
                onClick = onEnableBiometric,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("👆", fontSize = 18.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Enable Biometric Login",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Maybe Later
            TextButton(onClick = onMaybeLater) {
                Text(
                    "Maybe Later",
                    fontSize = 16.sp,
                    color = TextSecondary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Privacy note
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(NavyCard)
                    .padding(12.dp)
            ) {
                Text(
                    text = "Your biometric information is encrypted and stored securely on your device. FORTIFY never has access to your biometric data.",
                    fontSize = 12.sp,
                    color = TextMuted,
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun BiometricFeatureItem(icon: String, title: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(NavyCard)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(NavyCardLight),
            contentAlignment = Alignment.Center
        ) {
            Text(icon, fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = description,
                fontSize = 13.sp,
                color = TextSecondary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BiometricScreenPreview() {
    FortifyTheme {
        BiometricScreen()
    }
}