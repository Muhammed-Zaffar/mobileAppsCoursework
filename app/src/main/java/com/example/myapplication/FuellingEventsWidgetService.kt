package com.example.myapplication

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import android.net.Uri
import android.util.Log
import com.example.myapplication.R


class FuellingEventsWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return FuellingEventsViewsFactory(this.applicationContext, intent)
    }
}

class FuellingEventsViewsFactory(
    private val context: Context,
    private val intent: Intent
) : RemoteViewsService.RemoteViewsFactory {

    private var events: Cursor? = null
    // Q: what is the purpose of the events variable? What is it used for?
    // A: The events variable is used to store the data retrieved from the content provider.
    //    It is used to populate the list items in the widget.
    // Q: how does it get that data? do i not need to use the content provider to get the data?
    // A: The data is retrieved from the content provider in the onDataSetChanged() method.
    //    The content provider is used to query the data source and return a Cursor object.
    //    The RemoteViewsFactory then uses this Cursor to populate the list items in the widget.


    override fun onCreate() {
        // Connect to data source, usually initiated in onCreate()
        onDataSetChanged()
        getViewAt(0)
    }

    override fun onDataSetChanged() {
        // Refresh your data here. Called when onUpdate is triggered in AppWidgetProvider.
        events?.close()
        val uri = Uri.parse("content://com.example.myapplication.fuellingeventprovider/fuelling_events")
        events = context.contentResolver.query(uri, null, null, null, null)
        Log.d("NewAppWidget", "events: $events")
    }

    override fun onDestroy() {
        events?.close()
    }

    override fun getCount(): Int = events?.count ?: 0

    override fun getViewAt(position: Int): RemoteViews {
        // Create an instance of RemoteViews for the individual list item.
        val views = RemoteViews(context.packageName, R.layout.new_app_widget)
        Log.d("FuellingEventsViewsFactory", "events: $events")

        // Ensure the cursor is at the correct position
        events?.moveToPosition(position)

        // Get column index safely
        val descriptionIndex = events?.getColumnIndex("fuelStation") ?: -1
//
//        if (events != null && events!!.moveToPosition(position)) {
//            val eventDescription = events!!.getString(events!!.getColumnIndexOrThrow("description"))
//            // Assuming you have a TextView with an ID of event_description in your widget_item.xml
//            views.setTextViewText(R.id.events_list, eventDescription)
//        }
        views.setTextViewText(R.id.events_list, descriptionIndex.toString())

        // Example of setting up a fill-in intent, which reacts to clicks on items within the widget
        val fillInIntent = Intent().apply {
            putExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                intent.getIntExtra(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID
                )
            )
            setData(Uri.parse(this.toUri(Intent.URI_INTENT_SCHEME)))
        }
        views.setOnClickFillInIntent(R.id.events_list, fillInIntent)

        return views
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = position.toLong()

    override fun hasStableIds(): Boolean = true
}
