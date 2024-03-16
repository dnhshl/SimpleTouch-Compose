package com.example.simpletoucn.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.simpletoucn.R
import com.example.simpletoucn.model.MainViewModel
import com.example.simpletoucn.model.ScoreListItem


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DetailsScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val scoreList by viewModel.scoreList.collectAsState(initial = emptyList<ScoreListItem>())

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .weight(1f)
        ) {
            // Liste befüllen mit den Elementen der unserer ToDo Liste
            items(scoreList) { ListItemCard(score = it) }
        }
    }
}


// Composable for a single list item
@Composable
fun ListItemCard(
    score: ScoreListItem
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(id = R.string.scoreListEntry, score.score, score.nClicks, score.radius ))
        }
    }
}

