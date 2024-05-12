package com.example.myapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import com.example.myapplication.data.FuellingEvent
import android.app.DatePickerDialog
import android.content.Context
import android.widget.TextView
import androidx.compose.foundation.clickable
import java.util.*  // For using Calendar
import androidx.compose.runtime.remember
import androidx.compose.runtime.MutableState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.clickable



fun ShowDatePicker(
    context: Context,
    dateState: MutableState<String>
) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, dayOfMonth ->
            // Update dateState with selected date
            dateState.value = "${dayOfMonth}/${selectedMonth + 1}/${selectedYear}"
        }, year, month, day
    )

    datePickerDialog.show()

}

@Composable
fun AddEventScreen() {
    val context = LocalContext.current
    // Create a new FuellingEvent object
    var date by remember { mutableStateOf("") }
    var mileage by remember { mutableStateOf("") }
    var fuelStation by remember { mutableStateOf("") }
    var fuelType by remember { mutableStateOf("") }
    var litres by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var totalCost by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
//            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)  // Additional padding can be added as needed
            ) {
                Text(
                    text = "Add a new event",
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght))

                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            // Add a form to add a new event
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    readOnly = true,
                    label = {
                        Text(
                            text = "Date",
                            style = MaterialTheme.typography.bodyMedium,
                            fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght))
                        )
                    },
                    trailingIcon = {
                        Icon(Icons.Filled.DateRange, contentDescription = "Select Date",
                            modifier = Modifier.clickable { ShowDatePicker(context, mutableStateOf(date)) })
                    },
                    modifier = Modifier
                        .padding(start = 10.dp, bottom = 10.dp)
                        .fillMaxWidth(0.75f)
                        .clickable { ShowDatePicker(context, mutableStateOf(date)) }  // Open DatePicker when the TextField is clicked
                    ,
                )

                Row {
                    OutlinedTextField(
                        value = mileage, onValueChange = { mileage = it },
                        label = {
                            Text(
                                text = "Mileage",
                                style = MaterialTheme.typography.bodyMedium,
                                fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght))
                            )
                        },
                        modifier = Modifier
                            .padding(start = 10.dp, bottom = 10.dp)
                            .fillMaxWidth(0.75f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                        value = fuelStation, onValueChange = { fuelStation = it },
                        label = {
                            Text(
                                text = "Fuel Station",
                                style = MaterialTheme.typography.bodyMedium,
                                fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght))
                            )
                        },
                        modifier = Modifier
                            .padding(start = 10.dp, bottom = 10.dp)
                            .fillMaxWidth(0.75f),
                    )
                }

                Row {
                    OutlinedTextField(
                        value = fuelType, onValueChange = { fuelType = it },
                        label = {
                            Text(
                                text = "Fuel Type",
                                style = MaterialTheme.typography.bodyMedium,
                                fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght))
                            )
                        },
                        modifier = Modifier
                            .padding(start = 10.dp, bottom = 10.dp)
                            .fillMaxWidth(0.75f),
                    )
                }

                Row {
                    OutlinedTextField(
                        value = litres, onValueChange = { litres = it },
                        label = {
                            Text(
                                text = "Litres",
                                style = MaterialTheme.typography.bodyMedium,
                                fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght))
                            )
                        },
                        modifier = Modifier
                            .padding(start = 10.dp, bottom = 10.dp)
                            .fillMaxWidth(0.75f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                    Text(
                        text = "litres",
                        modifier = Modifier.padding(start = 10.dp, top = 20.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght))
                    )
                }

                Row {
                    OutlinedTextField(
                        value = price, onValueChange = { price = it },
                        label = {
                            Text(
                                text = "Price",
                                style = MaterialTheme.typography.bodyMedium,
                                fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght))
                            )
                        },
                        modifier = Modifier
                            .padding(start = 10.dp, bottom = 10.dp)
                            .fillMaxWidth(0.75f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                        value = totalCost, onValueChange = { totalCost = it },
                        label = {
                            Text(
                                text = "Total Cost",
                                style = MaterialTheme.typography.bodyMedium,
                                fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght))
                            )
                        },
                        modifier = Modifier
                            .padding(start = 10.dp, bottom = 10.dp)
                            .fillMaxWidth(0.75f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                    Text(
                        text = "GBP",
                        modifier = Modifier.padding(start = 10.dp, top = 20.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght))
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewAddEventScreen() {
    AddEventScreen()
}