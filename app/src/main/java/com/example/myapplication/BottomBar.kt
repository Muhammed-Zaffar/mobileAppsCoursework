package com.example.myapplication

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun BottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "Unknown"
    val isCalculatorSelected = currentRoute == "calculator"
    val isTimelineSelected = currentRoute == "timeline"
    val isSettingsSelected = currentRoute == "settings"
    Log.d("BottomBar", "Current route: $currentRoute")

    NavigationBar {
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.calculator_nav_icon),
                    contentDescription = "calculator",
                    modifier = Modifier
                        .padding(start = 2.dp, end = 6.dp, top = 2.dp, bottom = 2.dp)
                        .size(28.dp)
                )
            },
            label = { Text("calculator") },
            selected = isCalculatorSelected,
            onClick = {
                navController.navigate("calculator")
            },
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.timeline_nav_icon),
                    contentDescription = "timeline",
                    modifier = Modifier
                        .padding(start = 2.dp, end = 6.dp, top = 2.dp, bottom = 2.dp)
                        .size(28.dp)
                )
            },
            label = { Text("timeline") },
            selected = !isCalculatorSelected,
            onClick = {
                navController.navigate("timeline")
            },
        )
    }
}