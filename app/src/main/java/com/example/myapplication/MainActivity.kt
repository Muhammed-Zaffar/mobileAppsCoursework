package com.example.myapplication

import com.example.myapplication.data.FuellingEvent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.data.FuellingEventViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Main()
        }
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

            TimelineScreen(
                nav, listOf()
//                    FuellingEvent(
//                        date = "02/05/2024",
//                        mileage = 75000,
//                        fuelStation = "Costco - Leicester",
//                        fuelType = "Regular Unleaded (E10)",
//                        litres = 36.60,
//                        price = 1.419,
//                        totalCost = 51.94,
//                        imageUri = listOf()
//                    ),
//                    FuellingEvent(
//                        date = "23/03/2024",
//                        mileage = 74000,
//                        fuelStation = "Costco - Leicester",
//                        fuelType = "Premium Unleaded (E5)",
//                        litres = 44.22,
//                        price = 1.489,
//                        totalCost = 65.84,
//                        imageUri = listOf()
//                    )
//                )
            )

        }
        composable("addEvent") {
            val view_model = viewModel(modelClass = FuellingEventViewModel::class.java)
            AddEventScreen(nav, view_model)
        }
        // Add more composable routes as needed
    }
}

//TODO:
//touch and hold on an event to bring up a menu to:
//    share
//    edit
//    delete