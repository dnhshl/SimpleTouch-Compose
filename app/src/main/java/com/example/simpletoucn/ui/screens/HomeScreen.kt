package com.example.simpletoucn.ui.screens

import android.content.res.Resources
import android.os.SystemClock
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.simpletoucn.model.MainViewModel
import com.example.simpletoucn.model.ScoreListItem
import kotlin.math.roundToInt
import kotlin.math.sqrt
import kotlin.random.Random


@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MainViewModel
) {

    var canvasSize by remember { mutableStateOf(IntSize.Zero) }
    var clickPosition by remember { mutableStateOf<Offset?>(null) }
    var circlePosition by remember { mutableStateOf(Offset(0f,0f)) }
    var clickCounter by remember { mutableStateOf(0) }
    //var clickCount by remember { mutableStateOf(0) }

    val circleColor by viewModel.circleColor.collectAsState(Color.Red)
    val backgroundColor by viewModel.backgroundColor.collectAsState(Color.Blue)
    val circleRadius by viewModel.circleRadius.collectAsState(initial = 50f)
    val numberClicks by viewModel.numberClicks.collectAsState(initial = 5)

    circlePosition = Offset(canvasSize.width/2f, canvasSize.height/2f)

    var start = 0L
    var stop = 0L
    var gameStarted = false
    var clickCount = 0



    fun handleClick() {
        if (isCircleClick(circlePosition, clickPosition!!, circleRadius)) {
            if (!gameStarted) {
                gameStarted = true
                start = SystemClock.uptimeMillis()
            }
            clickCount++
            circlePosition = randomOffset(canvasSize, circleRadius)

            if (clickCount == numberClicks) {
                gameStarted = false
                stop = SystemClock.uptimeMillis()
                val time = stop - start
                val result = ScoreListItem(time = time, nClicks = clickCount, radius = circleRadius)
                viewModel.addToList(result)
                viewModel.showSnackbar("$result", duration = SnackbarDuration.Indefinite)
                circlePosition = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
            }
        }
    }




    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Canvas(modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .pointerInput(Unit) { // Enable pointer/touch events
                detectTapGestures {
                    clickPosition = it
                    handleClick()
                }
            }
            .onSizeChanged { canvasSize = it }
        ) {

            drawCircle(
                color = circleColor,
                radius = circleRadius,
                center = circlePosition
            )
        }

    }

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