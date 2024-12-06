package com.asherpope.ccc_av_team_android.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RoundedButton(
    text: String,
    tint: Color,
    action: () -> Unit
) {
    val isDarkTheme = isSystemInDarkTheme()

    val backgroundTint = if (tint == Color.Unspecified) {
        Color.Unspecified
    } else {
        tint.copy(alpha = 0.25f)
    }

    val foregroundTint = if (tint == Color.Unspecified) {
        if (isDarkTheme) Color.Black else Color.White
    } else {
        tint
    }

    Text(
        text = text,
        color = foregroundTint,
        fontWeight = FontWeight.SemiBold,
        fontFamily = FontFamily.Monospace,
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .background(backgroundTint)
            .clickable(onClick = action)
            .padding(horizontal = 28.dp, vertical = 24.dp)
    )
}

// Preview
@Composable
@Preview
fun RoundedButtonPreview() {
    RoundedButton(text = "next", tint = Color(0xFF3EB489)) { }
}

// End of file. No additional code.