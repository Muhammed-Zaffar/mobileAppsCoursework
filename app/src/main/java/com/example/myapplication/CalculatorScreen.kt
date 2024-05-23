package com.example.myapplication

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.data.FuellingEvent

@Composable
fun CalculatorScreen(navController: NavController) {
    // State variables to store input values
    var distance by remember { mutableStateOf("") }
    var fuelPrice by remember { mutableStateOf("") }
    var fuelConsumption by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }

    var distanceFocusRequester = FocusRequester()
    var fuelPriceFocusRequester = FocusRequester()
    var fuelConsumptionFocusRequester = FocusRequester()
    val keyboardController = LocalSoftwareKeyboardController.current


    Scaffold(
        bottomBar = { BottomBar(navController = navController) },
        topBar = { SimpleTopAppBar(title = "Trip calculator", navController = navController) },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
            ) {
                Text(
                    text = "Trip Calculator",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(bottom = 16.dp),
                    fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght))
                )

                Card(
                    modifier = Modifier
                        .padding(vertical = 16.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row {
                            OutlinedTextField(
                                value = distance,
                                onValueChange = { distance = it },
                                label = {
                                    Text(
                                        text = "Distance",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght)),
                                        modifier = Modifier.padding(bottom = 20.dp)
                                    )
                                },
                                modifier = Modifier
                                    .padding(start = 10.dp, bottom = 10.dp)
                                    .fillMaxWidth(0.75f)
                                    .focusRequester(distanceFocusRequester),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(onNext = {
                                    fuelPriceFocusRequester.requestFocus()
                                })
                            )
                            Text(
                                text = "miles",
                                modifier = Modifier.padding(start = 10.dp, top = 20.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght))
                            )
                        }
                        Row {
                            OutlinedTextField(
                                value = fuelPrice,
                                onValueChange = { fuelPrice = it },
                                label = {
                                    Text(
                                        text = "Fuel Price",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght)),
                                        modifier = Modifier.padding(bottom = 20.dp)
                                    )
                                },
                                modifier = Modifier
                                    .padding(start = 10.dp, bottom = 10.dp)
                                    .fillMaxWidth(0.75f)
                                    .focusRequester(fuelPriceFocusRequester),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(onNext = {
                                    fuelConsumptionFocusRequester.requestFocus()
                                })
                            )
                            Text(
                                text = "GBP",
                                modifier = Modifier.padding(start = 10.dp, top = 20.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght))
                            )
                        }
                        Row {
                            OutlinedTextField(
                                value = fuelConsumption,
                                onValueChange = { fuelConsumption = it },
                                label = {
                                    Text(
                                        text = "Fuel Economy",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght)),
                                        modifier = Modifier.padding(bottom = 20.dp),
                                    )
                                },
                                modifier = Modifier
                                    .padding(start = 10.dp, bottom = 10.dp)
                                    .fillMaxWidth(0.75f)
                                    .focusRequester(fuelConsumptionFocusRequester),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(onDone = {
                                    fuelConsumptionFocusRequester.freeFocus()
                                    keyboardController?.hide()
                                })
                            )
                            Text(
                                text = "mpg",
                                modifier = Modifier.padding(start = 10.dp, top = 20.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght))
                            )
                        }
                    }
                }

                Button(
                    onClick = {
                        val result =
                            if (distance.isNotEmpty() && fuelPrice.isNotEmpty() && fuelConsumption.isNotEmpty()) {
                                val d = distance.toDouble() * 1.60934
                                val c = 282.481f / fuelConsumption.toDouble()
                                val totalCost =
                                    ((d * c) / 100) * fuelPrice.toDouble()
                                "Total cost: £${FuellingEvent.formatToTwoDecimalPlace(totalCost.toDouble())}"
                            } else {
                                showDialog = true
                                "Please fill all fields"
                            }
                        answer = result
                        Log.d("CalculatorScreen", "Result: $result") // Log the result
//                        showDialog = true
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "calculate",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght))
                    )
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                ) {
                    Row {
                        Text(
                            text = answer,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght)),
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text("Incomplete Form") },
                        text = { Text("Please fill all fields before submitting.") },
                        confirmButton = {
                            Button(
                                onClick = { showDialog = false }
                            ) {
                                Text("OK")
                            }
                        }
                    )
                }

            }
        })
}

@Preview
@Composable
fun PreviewCalculatorScreen() {
//    CalculatorScreen()
}