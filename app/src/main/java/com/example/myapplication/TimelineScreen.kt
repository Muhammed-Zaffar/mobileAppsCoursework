package com.example.myapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.FuellingEvent

@Composable
fun TimelineScreen(fuellingEvents: List<FuellingEvent>) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Handle click here */ },
                content = {
                    Text("+", style = MaterialTheme.typography.bodyMedium)
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        content = { paddingValues ->  // Add this content lambda parameter
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)  // Use paddingValues provided by the Scaffold
                    .padding(16.dp)  // Additional padding can be added as needed
            ) {
                Text(
                    text = "Fuel Timeline",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(bottom = 16.dp),
                    fontFamily = FontFamily(Font(R.font.jetbrainsmono_variablefont_wght))
                )

                LazyColumn {
                    items(fuellingEvents.size) { index ->
                        val event = fuellingEvents[index]
                        FuellingEventItem(event)
                    }
                }
            }
        }
    )
}

@Composable
fun FuellingEventItem(event: FuellingEvent) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
//        elevation = 4.dp,
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
}

@Preview
@Composable
fun PreviewTimelineScreen() {
    // Preview the TimelineScreen with dummy data
    println("hello")

    TimelineScreen(
        listOf(
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
