package com.echipappa.fortify.ui.risk

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.echipappa.fortify.ui.theme.*

@Composable
fun RiskScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NavyDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "Digital Risk Score",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Understanding your digital security posture",
                fontSize = 16.sp,
                color = TextSecondary,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Circular score
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(NavyCard)
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    RiskCircle(score = 72)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("⚠️", fontSize = 24.sp)
                    Text(
                        text = "Fair",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = WarningYellow
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // What does this mean
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(NavyCard)
                    .padding(20.dp)
            ) {
                Text(
                    text = "What does this mean?",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Your risk score reflects how well you're managing your digital presence. A score of 72 indicates you're doing fairly well, but there's room for improvement.",
                    fontSize = 14.sp,
                    color = TextSecondary,
                    lineHeight = 22.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Improve score card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(NavyCard)
                    .padding(20.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(NavyCardLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("📈", fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Improve Your Score",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )
                        Text(
                            text = "Follow our recommendations to strengthen your digital security and reduce potential risks.",
                            fontSize = 13.sp,
                            color = TextSecondary,
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Risk breakdown
            Text(
                text = "Risk Breakdown",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            RiskFactorItem("💳", "Too many subscriptions", "6 active subscriptions detected", DangerRed, 70)
            Spacer(modifier = Modifier.height(10.dp))
            RiskFactorItem("👤", "Inactive accounts", "3 accounts with no recent activity", WarningYellow, 50)
            Spacer(modifier = Modifier.height(10.dp))
            RiskFactorItem("✉️", "Reused email", "Same email on 6 platforms", WarningYellow, 45)
            Spacer(modifier = Modifier.height(10.dp))
            RiskFactorItem("🔒", "Password strength", "No weak passwords detected", SuccessGreen, 20)

            Spacer(modifier = Modifier.height(24.dp))

            // Improve button
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    "Improve Score",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun RiskCircle(score: Int) {
    val sweepAngle = (score / 100f) * 270f

    Box(
        modifier = Modifier
            .size(180.dp)
            .drawBehind {
                val strokeWidth = 16.dp.toPx()
                val diameter = size.minDimension - strokeWidth
                val topLeft = Offset(
                    (size.width - diameter) / 2,
                    (size.height - diameter) / 2
                )
                val arcSize = Size(diameter, diameter)

                // Background arc
                drawArc(
                    color = NavyCardLight,
                    startAngle = 135f,
                    sweepAngle = 270f,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )

                // Score arc
                drawArc(
                    color = ElectricBlue,
                    startAngle = 135f,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$score",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Text(
                text = "/ 100",
                fontSize = 16.sp,
                color = TextSecondary
            )
        }
    }
}

@Composable
fun RiskFactorItem(icon: String, title: String, description: String, color: Color, risk: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(NavyCard)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(NavyCardLight),
                contentAlignment = Alignment.Center
            ) {
                Text(icon, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                Text(description, fontSize = 12.sp, color = TextSecondary)
            }
            Text(
                text = "$risk%",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        LinearProgressIndicator(
            progress = { risk / 100f },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = color,
            trackColor = NavyCardLight
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RiskScreenPreview() {
    FortifyTheme {
        RiskScreen()
    }
}