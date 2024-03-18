package com.example.simpletoucn.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.simpletoucn.R
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.drawColorIndicator
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

// Color Picker Dialog unter Nutzung der ColorPicker Compose Library
// https://github.com/skydoves/colorpicker-compose


@Composable
fun ColorPickerDialog(
    title: String,
    initialColor: Color,
    onDismissRequest: () -> Unit,
    onColorSelected: (Color) -> Unit
) {
    var color by remember { mutableStateOf(initialColor) }
    val controller = rememberColorPickerController()

    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Surface( modifier = Modifier.fillMaxHeight(0.9f)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp), // Add appropriate padding
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(text = title, style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.weight(1f))
                Row(modifier = Modifier.weight(1f)) {
                    listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Gray)
                        .forEach { color ->
                            Surface(
                                modifier = Modifier
                                    .height(40.dp)
                                    .weight(1f)
                                    .padding(horizontal = 4.dp),
                                color = color,
                                shape = RoundedCornerShape(6.dp),
                                onClick = {
                                    controller.selectByColor(color, true)
                                },
                            ) {}
                        }
                }

                Box(modifier = Modifier.weight(8f)) {
                    HsvColorPicker(
                        modifier = Modifier
                            .padding(10.dp),
                        controller = controller,
                        drawOnPosSelected = {
                            drawColorIndicator(
                                controller.selectedPoint.value,
                                controller.selectedColor.value,
                            )
                        },
                        onColorChanged = { colorEnvelope ->
                            color = colorEnvelope.color
                        },
                        initialColor = Color.Red,
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                AlphaTile(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .align(Alignment.CenterHorizontally),
                    controller = controller,
                )
                Spacer(modifier = Modifier.weight(1f))


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismissRequest) {
                        Text(stringResource(id = R.string.dismissButton))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    TextButton(onClick = {
                        onColorSelected(color)
                        onDismissRequest()
                    }) {
                        Text(stringResource(id = R.string.confirmButton))
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}


