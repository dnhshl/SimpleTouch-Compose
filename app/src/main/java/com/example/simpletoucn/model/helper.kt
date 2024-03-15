package com.example.simpletoucn.model

import androidx.compose.ui.graphics.Color

fun hexToComposeColor(hexColor: String): Color {
    val androidColor = android.graphics.Color.parseColor(hexColor)
    return Color(androidColor)
}