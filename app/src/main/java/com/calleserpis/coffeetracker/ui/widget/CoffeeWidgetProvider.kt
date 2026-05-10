package com.calleserpis.coffeetracker.ui.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.calleserpis.coffeetracker.MainActivity
import com.calleserpis.coffeetracker.R
import com.calleserpis.coffeetracker.domain.repository.CoffeeRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class CoffeeWidgetProvider : AppWidgetProvider() {

    @Inject
    lateinit var repository: CoffeeRepository

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        val pendingResult = goAsync()
        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            try {
                val todayCount = repository.getCoffeeCountToday()
                val totalCount = repository.getCoffeeCount()
                withContext(Dispatchers.Main) {
                    for (appWidgetId in appWidgetIds) {
                        updateAppWidget(
                            context,
                            appWidgetManager,
                            appWidgetId,
                            todayCount,
                            totalCount
                        )
                    }
                }
            } finally {
                pendingResult.finish()
            }
        }
    }
}

private fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
    todayCount: Int,
    totalCount: Int
) {
    val views = RemoteViews(context.packageName, R.layout.widget_coffee_summary)
    views.setTextViewText(R.id.widget_title, context.getString(R.string.app_name))
    views.setTextViewText(R.id.widget_total_caption, context.getString(R.string.widget_total_caption))
    views.setTextViewText(R.id.widget_total_value, totalCount.toString())
    views.setTextViewText(
        R.id.widget_today_summary,
        context.getString(R.string.widget_today_line, todayCount)
    )
    views.setTextViewText(R.id.widget_add_action, context.getString(R.string.widget_tap_to_add))

    val openAppIntent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
    }
    val openAppPi = PendingIntent.getActivity(
        context,
        0,
        openAppIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    views.setOnClickPendingIntent(R.id.widget_root, openAppPi)

    val openAddIntent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        putExtra(MainActivity.EXTRA_OPEN_ADD, true)
    }
    val openAddPi = PendingIntent.getActivity(
        context,
        1,
        openAddIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    views.setOnClickPendingIntent(R.id.widget_add_action, openAddPi)

    appWidgetManager.updateAppWidget(appWidgetId, views)
}
