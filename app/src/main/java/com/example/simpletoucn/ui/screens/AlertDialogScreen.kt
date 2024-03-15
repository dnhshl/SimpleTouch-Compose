package com.example.simpletoucn.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.simpletoucn.model.MainViewModel
import com.example.simpletoucn.model.hexToComposeColor
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.ColorPickerController
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.drawColorIndicator
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Composable
fun AlertDialogScreen(
    navController: NavController,
    viewModel: MainViewModel
) {

    val controller = rememberColorPickerController()
    val circleColor by viewModel.circleColor.collectAsState(Color.Red)

    var color by remember { mutableStateOf(Color.Red) }

    AlertDialog(
        title = { Text("Info!") },
        text = {
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
                    Log.i(">>>", "color ${colorEnvelope.hexCode}")
                },
                initialColor = circleColor,
            )
        },
        onDismissRequest = { navController.popBackStack() },
        confirmButton = {
            TextButton(onClick = {
                viewModel.setCircleColor(color)
                navController.popBackStack()
            }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton( onClick = { navController.popBackStack() } ) {
                Text("Dismiss")
            }
        }
    )
}

