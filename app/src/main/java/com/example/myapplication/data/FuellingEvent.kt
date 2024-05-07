package com.example.myapplication.data

import java.text.DecimalFormat

class FuellingEvent(
    var date: String,
    var mileage: Double,
    var fuelStation: String,
    var fuelType: String,

    // these properties are related, so one can be calculated from the other two
    var litres: Double? = null,
    var price: Double? = null,
    var totalCost: Double? = null
) {
    private val df: DecimalFormat = DecimalFormat("0.00")

    init {
        // Ensure that the total cost is up to 2 decimal places
        totalCost = totalCost?.let { df.format(it).toDouble() }

        // Ensure that the price is up to 3 decimal places
        price = price?.let { df.format(it).toDouble() }

        // Ensure that the litres is up to 2 decimal places
        litres = litres?.let { df.format(it).toDouble() }

        // Calculate the missing value if any
        calculateMissingValue()
    }


    override fun toString(): String {
        return "Date: $date\nMileage: $mileage\nFuel Station: $fuelStation\nFuel Type: $fuelType\nLitres: $litres\nPrice: $price\nTotal Cost: $totalCost"
    }

    // Custom setter for litres
    var litresValue: Double?
        get() = litres
        set(value) {
            litres = value
            calculateMissingValue()
        }

    // Custom setter for price
    var priceValue: Double?
        get() = price
        set(value) {
            price = value
            calculateMissingValue()
        }

    // Custom setter for totalCost
    var totalCostValue: Double?
        get() = totalCost
        set(value) {
            totalCost = value
            calculateMissingValue()
        }

    // Function to calculate the missing value (litres, price, or totalCost)
    private fun calculateMissingValue() {
        // If litres and price are both not null, calculate totalCost
        if (litres != null && price != null && totalCost == null) {
            totalCost = litres!! * price!!
        }
        // If litres and totalCost are both not null, calculate price
        else if (litres != null && totalCost != null && price == null) {
            price = totalCost!! / litres!!
        }
        // If price and totalCost are both not null, calculate litres
        else if (price != null && totalCost != null && litres == null) {
            litres = totalCost!! / price!!
        }
    }
}
