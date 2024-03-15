package com.example.simpletoucn.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.example.simpletoucn.R
import com.example.simpletoucn.model.MainViewModel
import com.example.simpletoucn.ui.screens.AlertDialogScreen
import com.example.simpletoucn.ui.screens.DetailsScreen
import com.example.simpletoucn.ui.screens.HomeScreen
import com.example.simpletoucn.ui.screens.InfoScreen
import com.example.simpletoucn.ui.screens.OtherScreen1
import com.example.simpletoucn.ui.screens.OtherScreen2
import com.example.simpletoucn.ui.screens.SettingsScreen

sealed class NavDestination(
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

    object Home : NavDestination(
        route = "home",                          // eindeutige Kennung
        title = R.string.homeScreenTitle,        // Titel in der TopBar
        label = R.string.homeScreenLabel,        // Label in der BottomBar
        selectedIcon = Icons.Filled.Home,        // Icon in der BottomBar, wenn gewählt
        unselectedIcon =Icons.Outlined.Home,     // Icon in der BottomBar, wenn nicht gewählt
        // Lambda Funktion, über die der Screen aufgerufen wird
        content = { navController, viewModel -> HomeScreen(navController, viewModel) }
    )

    object Details : NavDestination(
        route = "detail",
        title = R.string.detailsScreenTitle,
        label = R.string.detailsScreenLabel,
        selectedIcon = Icons.Filled.CheckCircle,
        unselectedIcon = Icons.Outlined.CheckCircle,
        content = { navController, viewModel -> DetailsScreen(navController, viewModel) }
    )

    object Settings : NavDestination(
        route = "settings",
        title = R.string.settingsScreenTitle,
        label = R.string.settingsScreenLabel,
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        content = { navController, viewModel -> SettingsScreen(navController, viewModel) }
    )

    // FullScreens (benötigen keine Icons und kein Label;
    // dafür aber showArrowBack = true für den Zurück Pfeil in der TopBar

    object Screen1 : NavDestination(
        route = "other1",
        title = R.string.otherScreen1Title,
        showArrowBack = true,
        content = { navController, viewModel -> OtherScreen1(navController, viewModel) }
    )

    object Screen2 : NavDestination(
        route = "other2",
        title = R.string.otherScreen2Title,
        showArrowBack = true,
        content = { navController, viewModel -> OtherScreen2(navController, viewModel) }
    )

    // Dialog Screens

    object Info : NavDestination(
        route = "info",
        content = { navController, viewModel -> InfoScreen(navController, viewModel) }
    )

    object AlertDialog : NavDestination(
        route = "alert_dialog",
        content = { navController, viewModel -> AlertDialogScreen(navController, viewModel) }
    )
}


// Hier alle Bildschirme listen, über die in der Bottom Bar navigiert werden soll
val bottomBarNavDestinations = listOf (
    NavDestination.Home,
    NavDestination.Details,
    NavDestination.Settings,
)


// Hier alle Bildschirme listen, die als FullScreen Bildschirm angesprungen werden sollen
// wenn es keine gibt, dann
// val otherDestinations = emptyList<NavDestination>()
val otherDestinations = listOf (
    NavDestination.Screen1,
    NavDestination.Screen2
)


val navDestinations = bottomBarNavDestinations + otherDestinations


// Hier alle Dialogbilschirme listen
// wenn es keine gibt, dann
// val dialogDestinations = emptyList<NavDestination>()
val dialogDestinations = listOf (
    NavDestination.Info,
    NavDestination.AlertDialog
)

