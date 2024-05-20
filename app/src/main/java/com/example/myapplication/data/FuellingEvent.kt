package com.example.myapplication.data

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DecimalFormat
import java.sql.Date

@Entity(tableName = "fuelling_events")
data class FuellingEvent(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var date: Date,
    var mileage: Int,
    var fuelStation: String,
    var fuelType: String,

    var litres: Double,
    var price: Double,
    var totalCost: Double,
    var imageUri: List<Uri>? = listOf()
) {

    // format the double values to 2 decimal places
    fun formatDouble(value: Double): String {
        val df: DecimalFormat = DecimalFormat("0.00")
        return df.format(value)
    }
     fun formatDouble3(value: Double): String {
        val df3: DecimalFormat = DecimalFormat("0.000")
        return df3.format(value)
    }

    companion object {
        fun formatToTwoDecimalPlace(value: Double): String {
            val df: DecimalFormat = DecimalFormat("0.00")
            return df.format(value)
        }
    }

    override fun toString(): String {
        return "Date: $date\nMileage: $mileage\nFuel Station: $fuelStation\nFuel Type: $fuelType\nLitres: $litres\nPrice: $price\nTotal Cost: $totalCost"
    }
}
