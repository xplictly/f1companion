package com.example.f1companion.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Dashboard : Screen("dashboard", "Dashboard", Icons.Default.Home)
    object Drivers : Screen("drivers", "Drivers", Icons.Default.Person)
    object Circuits : Screen("circuits", "Circuits", Icons.Default.Place)
}
