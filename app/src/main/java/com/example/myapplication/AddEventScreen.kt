package com.example.myapplication

import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
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
import java.util.Calendar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.ImageView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import coil.compose.rememberImagePainter

fun checkIfAnyFieldIsEmpty(
    date: String, mileage: String, fuelStation: String,
    fuelType: String, litres: String, price: String, totalCost: String
): Boolean {
    return date.isEmpty() || mileage.isEmpty() || fuelStation.isEmpty() ||
            fuelType.isEmpty() || litres.isEmpty() || price.isEmpty() || totalCost.isEmpty()
}


fun ShowDatePicker(
    context: Context,
    dateState: MutableState<String>
) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
//    DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, dayOfMonth ->
            // Update dateState with selected date
            dateState.value = "${dayOfMonth}/${selectedMonth + 1}/${selectedYear}"
        }, year, month, day
//    ).show()
    )
    datePickerDialog.show()

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AddEventScreen(navController: NavController? = null) {
    val context = LocalContext.current
    var answer by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<List<Uri>>(listOf()) }

    val pickImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uri: List<@JvmSuppressWildcards Uri> ->
        imageUri = uri
        Log.d("ImagePickerIcon", "Image URI: $uri")
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            pickImageLauncher.launch("image/*")
            Log.d("ImagePickerIcon", "Permission granted")
        } else {
            Log.e("ImagePickerIcon", "Permission denied by user")
        }
    }

    // Create a new FuellingEvent object
    var date: MutableState<String> = remember { mutableStateOf("") }
    var mileage by remember { mutableStateOf("") }
    var fuelStation by remember { mutableStateOf("") }
    var fuelType by remember { mutableStateOf("") }
    var litres by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var totalCost by remember { mutableStateOf("") }

    // focus requesters
    val mileageFocusRequester = FocusRequester()
    val fuelStationFocusRequester = FocusRequester()
    val fuelTypeFocusRequester = FocusRequester()
    val litresFocusRequester = FocusRequester()
    val priceFocusRequester = FocusRequester()
    val totalCostFocusRequester = FocusRequester()
    val keyboardController = LocalSoftwareKeyboardController.current



    Scaffold(
        bottomBar = {
            // Add a bottom bar if needed
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { navController?.navigate("calculator") }) {
                    Text("Go to calculator")
                }
                Button(onClick = { navController?.navigate("timeline") }) {
                    Text("Go to timeline")
                }
            }
        },
        content = { paddingValues ->
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)  // Use paddingValues provided by the Scaffold
                    .verticalScroll(scrollState)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),  // Additional padding can be added as needed
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Add a new event",
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Justify,
                        fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght))
                    )
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
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
                            value = date.value,
                            onValueChange = { },
                            readOnly = true,
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                            label = {
                                Text(
                                    text = "Date",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght))
                                )
                            },
                            trailingIcon = {
                                Icon(Icons.Filled.DateRange, contentDescription = "Select Date",
                                    modifier = Modifier
                                        .clickable {
                                            ShowDatePicker(context, date)
                                            Log.d(
                                                "AddEventScreen",
                                                "Date: ${date.value}"
                                            ) // Log the selected date
                                        }
                                )
                            },
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .fillMaxWidth(0.75f)
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
                                    .padding(start = 10.dp)
                                    .fillMaxWidth(0.75f)
                                    .focusRequester(mileageFocusRequester),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(onNext = {
                                    fuelStationFocusRequester.requestFocus()
                                })
                            )
                            Text(
                                text = "miles",
                                modifier = Modifier
                                    .padding(start = 10.dp, top = 0.dp)
                                    .align(Alignment.CenterVertically),
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
                                    .padding(start = 10.dp)
                                    .fillMaxWidth(0.75f)
                                    .focusRequester(fuelStationFocusRequester),
                                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                                keyboardActions = KeyboardActions(onNext = {
                                    fuelTypeFocusRequester.requestFocus()
                                })
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
                                    .padding(start = 10.dp)
                                    .fillMaxWidth(0.75f)
                                    .focusRequester(fuelTypeFocusRequester),
                                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                                keyboardActions = KeyboardActions(onNext = {
                                    litresFocusRequester.requestFocus()
                                })
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
                                    .padding(start = 10.dp)
                                    .fillMaxWidth(0.75f)
                                    .focusRequester(litresFocusRequester),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(onNext = {
                                    priceFocusRequester.requestFocus()
                                })
                            )
                            Text(
                                text = "litres",
                                modifier = Modifier
                                    .padding(start = 10.dp, top = 0.dp)
                                    .align(Alignment.CenterVertically),
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
                                    .padding(start = 10.dp)
                                    .fillMaxWidth(0.75f)
                                    .focusRequester(priceFocusRequester),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(onNext = {
                                    totalCostFocusRequester.requestFocus()
                                })
                            )
                            Text(
                                text = "GBP",
                                modifier = Modifier
                                    .padding(start = 10.dp, top = 0.dp)
                                    .align(Alignment.CenterVertically),
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
                                    .padding(start = 10.dp)
                                    .fillMaxWidth(0.75f)
                                    .focusRequester(totalCostFocusRequester),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(onDone = {
                                    totalCostFocusRequester.freeFocus()
                                    keyboardController?.hide()
                                })
                            )
                            Text(
                                text = "GBP",
                                modifier = Modifier
                                    .padding(start = 10.dp, top = 0.dp)
                                    .align(Alignment.CenterVertically),
                                style = MaterialTheme.typography.bodyMedium,
                                fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght))
                            )
                        }

                        OutlinedTextField(
                            value = "",
                            onValueChange = {},
                            label = {
                                Text(
                                    text = "Upload pictures",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght))
                                )
                            },
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_photo_camera_24),
                                    contentDescription = "Upload pictures",
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .clickable {
                                            when {
                                                ContextCompat.checkSelfPermission(
                                                    context,
                                                    android.Manifest.permission.READ_MEDIA_IMAGES
                                                ) == PackageManager.PERMISSION_GRANTED -> {
                                                    pickImageLauncher.launch("image/*")
                                                }

                                                else -> {
                                                    requestPermissionLauncher.launch(
                                                        android.Manifest.permission.READ_MEDIA_IMAGES
                                                    )
                                                }
                                            }
                                        }
                                )
                            },
                            modifier = Modifier
                                .padding(start = 10.dp, bottom = 10.dp)
                                .fillMaxWidth(0.75f),
                            readOnly = true,
                            colors = OutlinedTextFieldDefaults.colors()
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.padding(8.dp)
                        ) {
                            imageUri.forEach { uri ->
                                Image(
                                    painter = rememberImagePainter(uri), // using coil to load images because it caches them
                                    contentDescription = "Uploaded image",
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .height(100.dp)
                                )
                            }
                        }
                    }
                }


                Button(
                    onClick = {
                        val result =
                            if (!checkIfAnyFieldIsEmpty(
                                    date.value,
                                    mileage,
                                    fuelStation,
                                    fuelType,
                                    litres,
                                    price,
                                    totalCost
                                )
                            ) {
                                // add the event to the list then sync with database
                                "Event added successfully"
                            } else {
                                Log.d(
                                    "AddEventScreen",
                                    "Date: ${date.value}"
                                ) // Log the selected date
                                "Please fill all fields"
                            }
                        answer = result
                        Log.d("AddEventScreen", "Result: $result") // Log the result
                        showDialog = true
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "add event",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght))
                    )
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
fun PreviewAddEventScreen() {
    AddEventScreen()
}