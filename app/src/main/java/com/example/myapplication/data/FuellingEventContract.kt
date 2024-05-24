package com.example.myapplication.data

import android.net.Uri

object FuellingEventContract {
    const val AUTHORITY = "com.example.myapplication.fuellingeventprovider"

    // Base content URI to access data in the provider
    val BASE_CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY")

    object FuellingEvents {
        const val PATH_FUELLING_EVENTS = "fuelling_events"
        val CONTENT_URI: Uri = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_FUELLING_EVENTS)

        // MIME types for returning multiple items or a single item
        const val CONTENT_TYPE = "vnd.android.cursor.dir/vnd.$AUTHORITY.$PATH_FUELLING_EVENTS"
        const val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.$AUTHORITY.$PATH_FUELLING_EVENTS"

        // Columns in the fuelling events table
        const val COLUMN_ID = "id"
        const val COLUMN_DATE = "date"
        const val COLUMN_MILEAGE = "mileage"
        const val COLUMN_FUEL_STATION = "fuelStation"
        const val COLUMN_FUEL_TYPE = "fuelType"
        const val COLUMN_LITRES = "litres"
        const val COLUMN_PRICE = "price"
        const val COLUMN_TOTAL_COST = "totalCost"
        const val COLUMN_IMAGE_URI = "imageUri"
    }
}
