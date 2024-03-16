package com.example.simpletoucn.ui.screens

import android.os.SystemClock
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntSize
import androidx.navigation.NavController
import com.example.simpletoucn.R
import com.example.simpletoucn.model.MainViewModel
import com.example.simpletoucn.model.ScoreListItem
import com.example.simpletoucn.model.UiText
import com.example.simpletoucn.model.isCircleClick
import com.example.simpletoucn.model.randomOffset
import com.example.simpletoucn.ui.navigation.MyNavDestination



@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MainViewModel
) {

    val context = LocalContext.current

    var canvasSize by remember { mutableStateOf(IntSize.Zero) }
    var clickPosition by remember { mutableStateOf<Offset?>(null) }
    var circlePosition by remember { mutableStateOf(Offset(0f, 0f)) }

    val circleColor by viewModel.circleColor.collectAsState(Color.Red)
    val backgroundColor by viewModel.backgroundColor.collectAsState(Color.Blue)
    val circleRadius by viewModel.circleRadius.collectAsState(initial = 50f)
    val numberClicks by viewModel.numberClicks.collectAsState(initial = 5)

    circlePosition = Offset(canvasSize.width / 2f, canvasSize.height / 2f)

    var clickCount = 0
    var gameStarted = false
    var start = 0L
    var stop = 0L

    // Zugriff von extern
    // lesen
    val introScreen by viewModel.introScreen.collectAsState()
    if (introScreen) {
        viewModel.disableIntroScreen()
        navController.navigate(MyNavDestination.Info.route)
    }

    fun handleClick() {
        Log.i(">>>>", "handling click")
        if (isCircleClick(circlePosition, clickPosition!!, circleRadius)) {
            if (!gameStarted) {
                gameStarted = true
                start = SystemClock.uptimeMillis()
            }
            clickCount++
            circlePosition = randomOffset(canvasSize, circleRadius)
            Log.i(">>>>", "$circlePosition")

            if (clickCount >= numberClicks) {
                // Things to do when game ended
                stop = SystemClock.uptimeMillis()
                val time = stop - start
                val score = ScoreListItem(time = time, nClicks = clickCount, radius = circleRadius)
                viewModel.addToList(score)
                val message = UiText.StringResource(
                    R.string.scoreListEntry,
                    score.score,
                    score.nClicks,
                    score.radius).asString(context = context)

                viewModel.showSnackbar(message, duration = SnackbarDuration.Indefinite)
                // reset game
                gameStarted = false
                clickCount = 0
                circlePosition = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
            }
        }
    }


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



