package com.example.myapplication

import com.example.myapplication.data.FuellingEvent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.data.FuellingEventViewModel
import com.example.myapplication.ui.theme.AppThemeManager
import com.example.myapplication.ui.theme.LocalAppTheme
import com.example.myapplication.ui.theme.MyApplicationTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val appThemeManager = remember { AppThemeManager(applicationContext) }
            MyApplicationTheme(appThemeManager.isDarkTheme.value) {
                ProvideAppThemeManager(appThemeManager) {
                    Main()
                }
            }
        }
    }
}

@Composable
fun ProvideAppThemeManager(appThemeManager: AppThemeManager, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalAppTheme provides appThemeManager) {
        content()
    }
}


@Preview(showBackground = true)
@Composable
fun Main(fuellingEventViewModel: FuellingEventViewModel? = null) {
//    val view_model = viewModel(modelClass = FuellingEventViewModel::class.java)
//    view_model.insert(
//        FuellingEvent(
//            date = "02/05/2024",
//            mileage = 75000,
//            fuelStation = "Costco - Leicester",
//            fuelType = "Regular Unleaded (E10)",
//            litres = 36.60,
//            price = 1.419,
//            totalCost = 51.94,
//            imageUri = listOf()
//        )
//    )
//    view_model.insert(
//        FuellingEvent(
//            date = "23/03/2024",
//            mileage = 74000,
//            fuelStation = "Costco - Leicester",
//            fuelType = "Premium Unleaded (E5)",
//            litres = 44.22,
//            price = 1.489,
//            totalCost = 65.84,
//            imageUri = listOf()
//        )
//    )
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = "timeline") {
        composable("calculator") {
            //TODO: pass in fuellingEventViewModel
            CalculatorScreen(nav)
        }
        composable("timeline") {
            val view_model = viewModel(modelClass = FuellingEventViewModel::class.java)
            TimelineScreen(
                nav, listOf(), view_model
            )

        }
        composable("addEvent") {
            val view_model = viewModel(modelClass = FuellingEventViewModel::class.java)
            AddEventScreen(nav, view_model)
        }
        composable("editEvent/{event.id}") {
            val view_model = viewModel(modelClass = FuellingEventViewModel::class.java)
            val eventID = it.arguments?.getString("event.id")
            val ID = eventID?.toInt() ?: 0
            EditEventScreen(nav, view_model, ID)

        }
        // Add more composable routes as needed
    }
}

//TODO:
//touch and hold on an event to bring up a menu to:
//    share
//    edit
//    delete