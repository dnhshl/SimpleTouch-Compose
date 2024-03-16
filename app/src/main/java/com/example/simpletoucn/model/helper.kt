package com.example.simpletoucn.model

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import kotlin.random.Random


fun Color.toHexString(): String {
    val argb = this.toArgb()
    return String.format("#%08X", argb)
}



fun isCircleClick(circlePosition: Offset, clickPosition: Offset, radius: Float): Boolean {
    val dx = circlePosition.x - clickPosition.x
    val dy = circlePosition.y - clickPosition.y
    return dx*dx + dy*dy < radius*radius
}

fun randomOffset(canvasSize: IntSize, radius: Float): Offset {
    val random = Random.Default
    val r = radius.toInt()
    val w = canvasSize.width - 2 * r
    val h = canvasSize.height - 2 * r
    return Offset(
        (random.nextInt(w) + r).toFloat(),
        (random.nextInt(h) + r).toFloat()
    )
}



sealed class UiText {
    data class StringValue(val str: String): UiText()
    class StringResource(@StringRes val resourceId: Int, vararg val args: Any): UiText()

    @Composable
    fun asString(): String {
        return when (this) {
            is StringValue -> str
            is StringResource -> stringResource(id = resourceId, formatArgs = *args)
        }
    }

    fun asString(context: Context): String {
        return when(this) {
            is StringValue -> str
            is StringResource -> context.getString(resourceId, *args)
        }
    }
}
