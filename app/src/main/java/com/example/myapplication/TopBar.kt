package com.example.myapplication

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myapplication.data.FuellingEvent
import com.example.myapplication.ui.theme.LocalAppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTopAppBar(title: String, navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "Unknown"
    val appThemeManager = LocalAppTheme.current

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.pump_icon),
                    contentDescription = "Title: Fuel Tracker",
                    modifier = Modifier
                        .size(32.dp)
                        .padding(end = 8.dp),
                    tint = Color.White
                )
                Text(
                    text = "Fuel Tracker",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght)),
                    color = Color.White
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = {
                if (currentRoute == "calculator") {
                    navController.navigate("calculator")
                } else if (currentRoute == "timeline") {
                    navController.navigate("timeline")
                } else if (currentRoute == "addEvent") {
                    navController.navigate("timeline")
                } else if (currentRoute == "editEvent/{event.id}") {
                    navController.navigate("timeline")
                } else {
                    navController.popBackStack()
                }
            }) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Return to previous screen",
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(onClick = { appThemeManager.toggleTheme() }) {
                Icon(
                    imageVector = if (appThemeManager.isDarkTheme.value) Icons.Outlined.LightMode else Icons.Outlined.DarkMode,
                    contentDescription = "Switch to dark mode",
                    tint = Color.White
                )
            }
        }
    )
}
