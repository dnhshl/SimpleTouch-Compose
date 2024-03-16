package com.example.simpletoucn.ui.screens

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.simpletoucn.R
import com.example.simpletoucn.model.MainViewModel

@Composable
fun InfoScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    AlertDialog(
        title = {
            Text(text = stringResource(id = R.string.infoScreenTitle))
        },
        text = {
            Text(text = stringResource(id = R.string.infoScreenText))
        },
        onDismissRequest = { navController.popBackStack() },
        confirmButton = {
            TextButton(
                onClick = { navController.popBackStack() }
            ) {
                Text("OK")
            }
        }
    )
}
