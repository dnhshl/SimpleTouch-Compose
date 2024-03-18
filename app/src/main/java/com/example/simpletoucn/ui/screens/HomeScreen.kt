package com.example.simpletoucn.ui.screens

import android.os.SystemClock
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.simpletoucn.model.MainViewModel
import com.example.simpletoucn.model.ScoreListItem
import com.example.simpletoucn.model.calcDistance
import com.example.simpletoucn.model.randomOffset
import com.example.simpletoucn.ui.navigation.MyNavDestination


@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MainViewModel
) {

    val context = LocalContext.current


    // Konfigurationsvariablen aus dem ViewModel
    val circleColor by viewModel.circleColor.collectAsState(Color.Red)
    val backgroundColor by viewModel.backgroundColor.collectAsState(Color.Blue)
    val circleRadius by viewModel.circleRadius.collectAsState(initial = 50f)
    val numberClicks by viewModel.numberClicks.collectAsState(initial = -1)

    // Screen lokale State Variablen
    var canvasSize by remember { mutableStateOf(IntSize.Zero) }
    var clickPosition by remember { mutableStateOf<Offset?>(null) }
    var circlePosition by remember { mutableStateOf(Offset(0f, 0f)) }

    // lokale Variablen für die Spiellogik, aber ohne Einfluss auf die Darstellung
    var clickCount = 0
    var gameStarted = false
    var start = 0L
    var stop = 0L

    circlePosition = Offset(canvasSize.width / 2f, canvasSize.height / 2f)


    // einmaliges Anzeigen des Info Bildschirms
    val introScreen by viewModel.introScreen.collectAsState()
    if (introScreen) {
        viewModel.disableIntroScreen()
        navController.navigate(MyNavDestination.Info.route)
    }

    // Spiellogik
    fun handleClick() {
        if (calcDistance(circlePosition, clickPosition!!) < circleRadius) {
            // Erster Klick startet das Spiel
            if (!gameStarted) {
                gameStarted = true
                start = SystemClock.uptimeMillis()
            }

            // bei jedem Klick wird hochgezählt und der Kreis
            // an einer neuen Position gezeichnet
            clickCount++
            circlePosition = randomOffset(canvasSize, circleRadius)

            // To Dos bei Spielende
            if (clickCount >= numberClicks) {
                // Zeitmessung
                stop = SystemClock.uptimeMillis()
                val time = stop - start

                gameStarted = false
                clickCount = 0
                circlePosition = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
                // Spielergebnis erfassen
                val score = ScoreListItem(time = time, nClicks = clickCount, radius = circleRadius)
                // Spielergebnis im ViewModel speichern
                viewModel.setCurrentScore(score)
                // zum Result Screen navigieren
                navController.navigate(MyNavDestination.ScoreResult.route)
            }
        }
    }

    // Compose Element, auf dem Grafikelemente gezeichnet werden können
    // wird automatisch neu dargestellt, wenn sich eine Statevariable
    // (backgroundColor, circleColor, circlePosition, circleRadius, ...) ändert
    Canvas(modifier = Modifier
        .fillMaxSize()
        .background(backgroundColor)
        // erfasse Klick auf die Canvas
        .pointerInput(Unit) { // Enable pointer/touch events
            detectTapGestures {
                clickPosition = it
                Log.i(">>>", "clickPosition $clickPosition")
                handleClick()
            }
        }
        // Größe der Canvas erfassen für geometrische Berechnungen
        .onSizeChanged { canvasSize = it }
    ) {
        drawCircle(
            color = circleColor,
            radius = circleRadius,
            center = circlePosition
        )
    }
}



