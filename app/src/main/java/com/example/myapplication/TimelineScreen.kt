package com.example.myapplication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.data.FuellingEvent
import kotlinx.coroutines.launch

@Composable
fun TimelineScreen(navController: NavController? = null, fuellingEvents: List<FuellingEvent>?) {
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
                    if (fuellingEvents != null) {
                        items(fuellingEvents.size) { index ->
                            val event = fuellingEvents.get(index)
                            FuellingEventItem(event)
                        }
                    }
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
fun FuellingEventItem(event: FuellingEvent) {
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
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState
        ) {
            // Sheet content
            Column(modifier = Modifier.padding(16.dp)) {
                Button(
                    onClick = { onEdit() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Text("Edit")
                }
                Button(
                    onClick = { onDelete() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Text("Delete")
                }
                Button(
                    onClick = { onShare() },
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

fun onShare() {
    TODO("Not yet implemented")
    // Implement the share functionality
}

fun onDelete() {
    TODO("Not yet implemented")
    // Implement the delete functionality
}

fun onEdit() {
    TODO("Not yet implemented")
    // Implement the edit functionality
}

@Preview
@Composable
fun PreviewTimelineScreen() {
//     Preview the TimelineScreen with dummy data

    TimelineScreen(
        fuellingEvents = listOf(
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
        )
    )
}
