package com.example.myapplication

import com.example.myapplication.data.FuellingEvent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GreetingPreview()
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
//    ActivityResultContracts.RequestPermission()
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = "timeline") {
        composable("calculator") { CalculatorScreen(nav) }
        composable("timeline") { TimelineScreen(nav, listOf(
            FuellingEvent(
                "02/05/2024",
                75000,
                "Costco - Leicester",
                "Regular Unleaded (E10)",
                36.60,
                1.419,
                51.94
            ),
            FuellingEvent(
                "23/03/2024",
                74950,
                "Costco - Leicester",
                "Premium Unleaded (E5)",
                44.22,
                1.489,
                65.84
            )
        )) }
        composable("addEvent") { AddEventScreen(nav) }
        // Add more composable routes as needed
    }
//    CalculatorScreen()
}

//TODO:
//allow adding pictures to an event
//touch and hold on an event to bring up a menu to:
//    share
//    edit
//    delete