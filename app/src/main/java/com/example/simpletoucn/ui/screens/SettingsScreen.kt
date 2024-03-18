package com.example.simpletoucn.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.simpletoucn.R
import com.example.simpletoucn.model.MainViewModel

const val CIRCLE = "circle"
const val BACKGROUND = "background"

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    var showCircleColorPicker by remember { mutableStateOf(false) }
    var showBackgroundColorPicker by remember { mutableStateOf(false) }
    val circleColor by viewModel.circleColor.collectAsState(Color.Red)
    val backgroundColor by viewModel.backgroundColor.collectAsState(Color.Blue)
    val circleRadius by viewModel.circleRadius.collectAsState(-1f)
    val numberClicks by viewModel.numberClicks.collectAsState( -1)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ColorDisplayRow(
            color = circleColor, 
            text = stringResource(id = R.string.adjustCircleColor)
        ) {
            showCircleColorPicker = true
        }

        ColorDisplayRow(
            color = backgroundColor, 
            circle = false,
            text = stringResource(id = R.string.adjustBackgroundColor)
        ) {
            showBackgroundColorPicker = true
        }


        Text(stringResource(id = R.string.adjustNumberClicks))

        if (numberClicks > 0) {
            AdjustNumberClicksRow(currentNumberClicks = numberClicks) {
                viewModel.setNumberClicks(it)
            }
        }
        
        if (circleRadius > 0) {
            AdjustRadiusRow(
                backgroundColor = backgroundColor,
                circleColor = circleColor,
                currentRadius = circleRadius
            ) { viewModel.setCircleRadius(it) }

        }
        if (showCircleColorPicker) {
            ColorPickerDialog(
                title = stringResource(id = R.string.colorPickerDialogTitle),
                initialColor = circleColor,
                onDismissRequest = { showCircleColorPicker = false },
                onColorSelected = {
                    viewModel.setCircleColor(it)
                    showCircleColorPicker = false
                }
            )
        }

        if (showBackgroundColorPicker) {
            ColorPickerDialog(
                title = stringResource(id = R.string.colorPickerDialogTitle),
                initialColor = backgroundColor,
                onDismissRequest = { showBackgroundColorPicker = false },
                onColorSelected = {
                    viewModel.setBackgroundColor(it)
                    showBackgroundColorPicker = false
                }
            )
        }

    }
}


@Composable
fun ColorDisplayRow(color: Color, text: String = "", circle:Boolean = true, onEditClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Canvas with Circle or Square
        Canvas(modifier = Modifier.size(70.dp)) {
            if (circle) {
                drawCircle(
                    color = color,
                    radius = size.minDimension / 2
                )
            }
            else {
                drawRect(
                    color = color,
                    size = Size(size.minDimension, size.minDimension)
                )
            }
        }

        Text(text)

        Spacer(modifier = Modifier.width(10.dp))

        Icon(
            Icons.Filled.Edit,
            contentDescription = "Edit Color",
            modifier = Modifier.clickable { onEditClick() }
        )
    }
}

@Composable
fun AdjustRadiusRow(
    backgroundColor: Color,
    circleColor: Color,
    currentRadius: Float,
    onRadiusChange: (Float) -> Unit
) {
    var circleRadius by remember { mutableStateOf(currentRadius) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(backgroundColor)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    color = circleColor,
                    radius = circleRadius,
                    center = Offset(size.width / 2, size.height / 2)
                )
            }
        }

        Column (
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            Slider(
                value = circleRadius,
                onValueChange = { newValue ->
                    circleRadius = newValue
                    onRadiusChange(newValue)
                },
                valueRange = 50f..200f,
                modifier = Modifier
                    .width(200.dp)
                    .rotate(90f)
                    .fillMaxHeight(0.7f)
            )
            Text(stringResource(id = R.string.adjustCircleRadius))
        }
    }
}

@Composable
fun AdjustNumberClicksRow(
    currentNumberClicks: Int,
    onNumberClicksChange: (Int) -> Unit
) {
    var numberOfClicks by remember { mutableStateOf(currentNumberClicks.toFloat()) } // Initial value 5
    val valueRange = 5f..50f

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {
        Slider(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .width(200.dp), // Fixed width
            value = numberOfClicks,
            onValueChange = {
                numberOfClicks = it
                onNumberClicksChange(it.toInt())
            },
            valueRange = valueRange,
            steps = (valueRange.endInclusive/5 - valueRange.start/5 - 1).toInt()
        )
        Spacer(modifier = Modifier.width(16.dp))
        Box(modifier = Modifier.width(50.dp)) {
            Text(text = "${numberOfClicks.toInt()}", modifier = Modifier.align(Alignment.Center))
        }
    }
}
