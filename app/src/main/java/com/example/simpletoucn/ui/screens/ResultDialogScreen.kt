package com.example.simpletoucn.ui.screens

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.simpletoucn.R
import com.example.simpletoucn.model.MainViewModel


@Composable
fun ResultDialogScreen(
    navController: NavController,
    viewModel: MainViewModel
) {

    val score by viewModel.currentScore.collectAsState()

    AlertDialog(
        title = { Text(stringResource(id = R.string.resultScreenTitle)) },
        text = {
            Text(
                stringResource(
                    id = R.string.resultScreenText,
                    score.nClicks,
                    score.time,
                    score.score
                )
            )
        },
        onDismissRequest = { navController.popBackStack() },
        confirmButton = {
            TextButton(
                onClick = {
                    viewModel.addToList(score)
                    navController.popBackStack()
                }
            ) {
                Text(stringResource(id = R.string.confirmButton))
            }
        },
        dismissButton = {
            TextButton( onClick = { navController.popBackStack() } ) {
                Text(stringResource(id = R.string.dismissButton))
            }
        }
    )
}
