package com.example.yandexsummerschool.ui.common.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun FloatingActionButton(navController: NavController) {
    FloatingActionButton(
        onClick = {
// 			navController.navigate("") {
// 				popUpTo(navController.graph.findStartDestination().id) {
// 					saveState = true
// 				}
// 				launchSingleTop = true
// 				restoreState = true
// 			}
        },
        shape = CircleShape,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.surface,
        elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp),
    ) {
        Icon(Icons.Filled.Add, null)
    }
}
