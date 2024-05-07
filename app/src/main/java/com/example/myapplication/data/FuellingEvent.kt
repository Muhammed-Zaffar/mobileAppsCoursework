package com.example.myapplication.data

import java.text.DecimalFormat

class FuellingEvent(
    var date: String,
    var mileage: Int,
    var fuelStation: String,
    var fuelType: String,

    var litres: Double,
    var price: Double,
    var totalCost: Double
) {

//    init {
//        formatValues()
//    }

    // format the double values to 2 decimal places
    fun formatDouble(value: Double): String {
        val df: DecimalFormat = DecimalFormat("0.00")
        return df.format(value)
    }
    fun formatDouble3(value: Double): String {
        val df3: DecimalFormat = DecimalFormat("0.000")
        return df3.format(value)
    }

    // format the double values to 2 decimal places
//    fun formatValues(): Unit {
//        this.litres = formatDouble(litres)
//        this.price = formatDouble3(price)
//        this.totalCost = formatDouble(totalCost)
//    }


    override fun toString(): String {
        return "Date: $date\nMileage: $mileage\nFuel Station: $fuelStation\nFuel Type: $fuelType\nLitres: $litres\nPrice: $price\nTotal Cost: $totalCost"
    }
}
