package com.example.myapplication

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableLongState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
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
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.myapplication.data.FuellingEvent
import com.example.myapplication.data.FuellingEventViewModel
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.absoluteValue

fun checkIfAnyFieldIsEmpty(
    date: String, mileage: String, fuelStation: String,
    fuelType: String, litres: String, price: String, totalCost: String
): Boolean {
    return date.isEmpty() || mileage.isEmpty() || fuelStation.isEmpty() ||
            fuelType.isEmpty() || litres.isEmpty() || price.isEmpty() || totalCost.isEmpty()
}


fun showDatePicker(
    context: Context,
    dateState: MutableState<Long>,
//    onDateSelected: (Long) -> Unit
) {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = dateState.value  // Initialize the calendar to the current date state
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, dayOfMonth ->
            // Update dateState with selected date
//            dateState.value = "${dayOfMonth}/${selectedMonth + 1}/${selectedYear}"
//            onDateSelected(calendar.timeInMillis)
            calendar.set(selectedYear, selectedMonth, dayOfMonth)
            dateState.value = calendar.timeInMillis
        }, year, month, day
    )
    datePickerDialog.show()

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddEventScreen(navController: NavController, view_model: FuellingEventViewModel = viewModel()) {
    val context = LocalContext.current
    var answer by rememberSaveable { mutableStateOf("") }
    var showDialog by rememberSaveable { mutableStateOf(false) }
    // State for the date as timestamp
    val dateTimestamp by rememberSaveable { mutableStateOf(System.currentTimeMillis()) }
    val dateTimestampState = rememberSaveable { mutableStateOf(dateTimestamp) }

    // Create a new FuellingEvent object
    val date = Date(dateTimestampState.value)  // Convert timestamp to java.sql.Date
    var mileage by rememberSaveable { mutableStateOf("") }
    var fuelStation by rememberSaveable { mutableStateOf("") }
    var fuelType by rememberSaveable { mutableStateOf("") }
    var litres by rememberSaveable { mutableStateOf("") }
    var price by rememberSaveable { mutableStateOf("") }
    var totalCost by rememberSaveable { mutableStateOf("") }
    var imageUri by rememberSaveable { mutableStateOf<List<Uri>>(listOf()) }

    // Activity result launcher for picking images
    val pickImageLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uri: List<@JvmSuppressWildcards Uri> ->
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

    // focus requesters
    val mileageFocusRequester = FocusRequester()
    val fuelStationFocusRequester = FocusRequester()
    val fuelTypeFocusRequester = FocusRequester()
    val litresFocusRequester = FocusRequester()
    val priceFocusRequester = FocusRequester()
    val totalCostFocusRequester = FocusRequester()
    val keyboardController = LocalSoftwareKeyboardController.current



    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { BottomBar(navController = navController) },
        topBar = { SimpleTopAppBar(title = "Add a new event", navController = navController) },
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
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
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
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Gray,
                                unfocusedBorderColor = Color.Gray,
                                focusedLabelColor = Color.Gray,
                                unfocusedLabelColor = Color.Gray,
                                cursorColor = Color.Gray
                            ),
                            value = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(
                                Date(
                                    dateTimestampState.value
                                )
                            ),
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
                                Icon(Icons.Outlined.DateRange, contentDescription = "Select Date",
                                    modifier = Modifier
                                        .clickable {
                                            showDatePicker(context, dateTimestampState)
                                            Log.d(
                                                "AddEventScreen",
                                                "Date: ${dateTimestampState.value} \n" +
                                                        SimpleDateFormat(
                                                            "dd/MM/yyyy",
                                                            Locale.getDefault()
                                                        ).format(Date(dateTimestampState.value))
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
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Gray,
                                    unfocusedBorderColor = Color.Gray,
                                    focusedLabelColor = Color.Gray,
                                    unfocusedLabelColor = Color.Gray,
                                    cursorColor = Color.Gray
                                ),
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
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Gray,
                                    unfocusedBorderColor = Color.Gray,
                                    focusedLabelColor = Color.Gray,
                                    unfocusedLabelColor = Color.Gray,
                                    cursorColor = Color.Gray
                                ),
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
                                }),
                                trailingIcon = {
                                    Icon(
                                        Icons.Outlined.LocationOn,
                                        contentDescription = "Location",
                                        modifier = Modifier
                                            .clickable {
                                                // Open the phone's default map app
                                                val gmmIntentUri =
                                                    Uri.parse("geo:0,0?q=gas_station $fuelStation")
                                                val mapIntent =
                                                    Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                                mapIntent.setPackage("com.google.android.apps.maps")
                                                context.startActivity(mapIntent)
                                                Log.d(
                                                    "AddEventScreen",
                                                    "Fuel Station: $fuelStation"
                                                ) // Log the fuel station
                                            }
                                    )
                                }
                            )
                        }

                        Row {
                            OutlinedTextField(
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Gray,
                                    unfocusedBorderColor = Color.Gray,
                                    focusedLabelColor = Color.Gray,
                                    unfocusedLabelColor = Color.Gray,
                                    cursorColor = Color.Gray
                                ),
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
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Gray,
                                    unfocusedBorderColor = Color.Gray,
                                    focusedLabelColor = Color.Gray,
                                    unfocusedLabelColor = Color.Gray,
                                    cursorColor = Color.Gray
                                ),
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
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Gray,
                                    unfocusedBorderColor = Color.Gray,
                                    focusedLabelColor = Color.Gray,
                                    unfocusedLabelColor = Color.Gray,
                                    cursorColor = Color.Gray
                                ),
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
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Gray,
                                    unfocusedBorderColor = Color.Gray,
                                    focusedLabelColor = Color.Gray,
                                    unfocusedLabelColor = Color.Gray,
                                    cursorColor = Color.Gray
                                ),
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
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Gray,
                                unfocusedBorderColor = Color.Gray,
                                focusedLabelColor = Color.Gray,
                                unfocusedLabelColor = Color.Gray,
                                cursorColor = Color.Gray
                            ),
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
                                    imageVector = Icons.Outlined.AddAPhoto,
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
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.padding(8.dp)
                        ) {
                            imageUri.forEach { uri ->
                                Image(
                                    painter = rememberAsyncImagePainter(uri), // using coil to load images because it caches them
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
                        answer =
                            if (!checkIfAnyFieldIsEmpty(
                                    date.toString(),
                                    mileage,
                                    fuelStation,
                                    fuelType,
                                    litres,
                                    price,
                                    totalCost
                                )
                            ) {
                                view_model.insert(
                                    FuellingEvent(
                                        date = date,
                                        mileage = mileage.toInt(),
                                        fuelStation = fuelStation,
                                        fuelType = fuelType,
                                        litres = litres.toDouble(),
                                        price = price.toDouble(),
                                        totalCost = totalCost.toDouble(),
                                        imageUri = imageUri
                                    )
                                )
                                // add the event to the list then sync with database
                                Log.d(
                                    "AddEventScreen",
                                    "imageUri: ${imageUri::class.java.typeName}"
                                )
                                navController.popBackStack()
                                "Event added successfully"
                            } else {
                                Log.d(
                                    "AddEventScreen",
                                    "Date: ${date}"
                                ) // Log the selected date
                                showDialog = true
                                "Please fill all fields"
                            }
                        Log.d("AddEventScreen", "Result: $answer") // Log the result
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
