package com.example.simpletoucn.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.example.simpletoucn.R
import com.example.simpletoucn.model.MainViewModel
import com.example.simpletoucn.ui.screens.HighscoreScreen
import com.example.simpletoucn.ui.screens.HomeScreen
import com.example.simpletoucn.ui.screens.InfoScreen
import com.example.simpletoucn.ui.screens.ResultDialogScreen
import com.example.simpletoucn.ui.screens.SettingsScreen

sealed class MyNavDestination(
    val route: String,
    val title: Int = 0,
    val label: Int = 0,
    val selectedIcon: ImageVector = Icons.Default.Check,
    val unselectedIcon: ImageVector = Icons.Default.Check,
    val showArrowBack: Boolean = false,
    val content: @Composable (NavController, MainViewModel) -> Unit
) {

    // hier alle Bildschirme mit den notwendigen Infos dazu listen

    // BottomNavScreens

    object Home : MyNavDestination(
        route = "home",                          // eindeutige Kennung
        title = R.string.homeScreenTitle,        // Titel in der TopBar
        label = R.string.homeScreenLabel,        // Label in der BottomBar
        selectedIcon = Icons.Filled.Home,        // Icon in der BottomBar, wenn gewählt
        unselectedIcon =Icons.Outlined.Home,     // Icon in der BottomBar, wenn nicht gewählt
        // Lambda Funktion, über die der Screen aufgerufen wird
        content = { navController, viewModel -> HomeScreen(navController, viewModel) }
    )

    object Top20 : MyNavDestination(
        route = "top20",
        title = R.string.top20ScreenTitle,
        label = R.string.top20ScreenLabel,
        selectedIcon = Icons.Filled.List,
        unselectedIcon = Icons.Outlined.List,
        content = { navController, viewModel -> HighscoreScreen(navController, viewModel) }
    )

    object Settings : MyNavDestination(
        route = "settings",
        title = R.string.settingsScreenTitle,
        label = R.string.settingsScreenLabel,
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        content = { navController, viewModel -> SettingsScreen(navController, viewModel) }
    )

    // FullScreens (benötigen keine Icons und kein Label;
    // dafür aber showArrowBack = true für den Zurück Pfeil in der TopBar


    // Dialog Screens

    object Info : MyNavDestination(
        route = "info",
        content = { navController, viewModel -> InfoScreen(navController, viewModel) }
    )

    object ScoreResult : MyNavDestination(
        route = "score_result",
        content = { navController, viewModel -> ResultDialogScreen(navController, viewModel) }
    )
}

// Hier alle Bildschirme listen, über die in der Bottom Bar navigiert werden soll
val bottomBarNavDestinations = listOf (
    MyNavDestination.Home,
    MyNavDestination.Top20,
    MyNavDestination.Settings,
)


// Hier alle Bildschirme listen, die als FullScreen Bildschirm angesprungen werden sollen
// wenn es keine gibt, dann
// val otherDestinations = emptyList<MyNavDestination>()
val otherDestinations = emptyList<MyNavDestination>()

val navDestinations = bottomBarNavDestinations + otherDestinations


// Hier alle Dialogbildschirme listen
// wenn es keine gibt, dann
// val dialogDestinations = emptyList<MyNavDestination>()
val dialogDestinations = listOf (
    MyNavDestination.Info,
    MyNavDestination.ScoreResult
)

