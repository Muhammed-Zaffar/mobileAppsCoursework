package com.example.myapplication

import android.util.Log
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.data.FuellingEvent
import com.example.myapplication.data.FuellingEventViewModel

@Composable
fun TimelineScreen(
    navController: NavController? = null,
    fuellingEvents: List<FuellingEvent>?,
    view_model: FuellingEventViewModel = viewModel()
) {
    val allFuellingEvents by view_model.allFuellingEvents.observeAsState()
    val showDeleteDialog = rememberSaveable { mutableStateOf(false) }
    val eventToDelete = rememberSaveable { mutableStateOf<FuellingEvent?>(null) }

    if (showDeleteDialog.value && eventToDelete.value != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog.value = false },
            title = { Text("Confirm Delete") },
            text = { Text("Are you sure you want to delete this event? This cannot be undone.") },
            confirmButton = {
                Button(onClick = {
                    view_model.delete(eventToDelete.value!!)
                    Log.d("TimelineScreen", "Deleted event: ${eventToDelete.value?.id}")
                    showDeleteDialog.value = false
                    eventToDelete.value = null
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(onClick = { showDeleteDialog.value = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    if (fuellingEvents != null) {
        fuellingEvents.forEach { event ->
            view_model.insert(event)
        }
    }

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = {
                navController?.navigate("addEvent")
            },
            content = {
                Text("+", style = MaterialTheme.typography.bodyMedium)
            })
    },
        floatingActionButtonPosition = FabPosition.End,
        content = { paddingValues ->  // Add this content lambda parameter
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)  // Use paddingValues provided by the Scaffold
                    .padding(16.dp)
            ) {
                Text(
                    text = "Fuel Timeline",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(bottom = 16.dp),
                    fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght))
                )

                LazyColumn {
//                    if (allFuellingEvents != null) {
//                        for (fuellingEvent in allFuellingEvents!!) {
//                            item {
//                                FuellingEventItem(fuellingEvent)
//                            }
//                        }
//                    }
                    items(allFuellingEvents ?: listOf()) { event ->
                        FuellingEventItem(navController, event, view_model, showDeleteDialog, eventToDelete)
                    }
                    Log.d("TimelineScreen", "allFuellingEvents: ${allFuellingEvents}")
                    Log.d("TimelineScreen", "allFuellingEvents.size: ${allFuellingEvents?.size}")
                }
            }
        },
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
        }

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FuellingEventItem(
    navController: NavController?,
    event: FuellingEvent,
    view_model: FuellingEventViewModel,
    showDeleteDialog: MutableState<Boolean>,
    eventToDelete: MutableState<FuellingEvent?>
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        showBottomSheet = true
                    },
                )
            },
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Refuelling",
                style = MaterialTheme.typography.titleLarge,
                fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght))
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Date: ${event.date}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght))
                )
                Text(
                    text = "Total Cost: £${event.formatDouble(event.totalCost)}",
                    fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght))
                )
            }
            Text(
                text = "Mileage: ${event.mileage}",
                textAlign = TextAlign.Right,
                fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght))
            )
            Text(
                text = "Fuel Station: ${event.fuelStation}",
                fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght))
            )
            Text(
                text = "Litres: ${event.formatDouble(event.litres)}",
                fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght))
            )
            Text(
                text = "Price: ${event.formatDouble3(event.price)}",
                fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght))
            )
            Text(
                text = "Fuel Type: ${event.fuelType}",
                fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght))
            )
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {
            // Sheet content
            Column(modifier = Modifier.padding(16.dp)) {
                Button(
                    onClick = { navController?.navigate("editEvent/${event.id}") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Text("Edit")
                }
                Button(
                    onClick = {
//                        view_model.delete(event)
                        eventToDelete.value = event
                        showDeleteDialog.value = true
                        showBottomSheet = false
//                        Log.d("TimelineScreen", "Deleted event: ${event.id}")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Text("Delete")
                }
                Button(
                    onClick = {  },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Text("Share")
                }
            }
        }
    }
}