package com.example.myapplication
import com.example.myapplication.data.FuellingEvent as FuellingEvent

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CalculatorScreen() {
    // State variables to store input values
    var distance by remember { mutableStateOf("") }
    var fuelPrice by remember { mutableStateOf("") }
    var fuelConsumption by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Calculate the cost of a trip",
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght))
                )
            }
        }


        Card(
            modifier = Modifier
                .fillMaxWidth()
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
                            .fillMaxWidth(0.75f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
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
                            .fillMaxWidth(0.75f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
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
                            .fillMaxWidth(0.75f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                            ((d * c ) / 100 ) * fuelPrice.toDouble()
                        "Total cost: £${FuellingEvent.formatToTwoDecimalPlace(totalCost.toDouble())}"
                    } else {
                        "Please fill all fields"
                    }
                answer = result
                Log.d("CalculatorScreen", "Result: $result") // Log the result
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

    }
}

@Preview
@Composable
fun PreviewCalculatorScreen() {
    CalculatorScreen()
}