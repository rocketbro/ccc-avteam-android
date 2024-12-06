package com.asherpope.ccc_av_team_android.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asherpope.ccc_av_team_android.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    pullData: Boolean,
    dataFetched: Boolean,
    onAppear: () -> Unit
) {
    var showProgress by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1000) // 1 second delay
        if (pullData) {
            showProgress = true
        }
        onAppear()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF1c3040).copy(alpha = 0.95f),
                        Color(0xFF1c3040).copy(alpha = 0.45f)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.ccc_white),
                contentDescription = "CCC Logo",
                modifier = Modifier
                    .height(275.dp)
                    .padding(vertical = 32.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "CCC Audiovisual Team",
                fontFamily = FontFamily.Monospace,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            AnimatedVisibility(visible = showProgress) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

// Preview
@Composable
@Preview
fun SplashScreenPreview() {
    SplashScreen(
        pullData = true,
        dataFetched = false,
        onAppear = {}
    )
}

// End of file. No additional code.