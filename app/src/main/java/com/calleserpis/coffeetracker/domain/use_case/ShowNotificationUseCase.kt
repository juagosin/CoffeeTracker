package com.calleserpis.coffeetracker.domain.use_case

import android.util.Log
import com.calleserpis.coffeetracker.data.notification.NotificationHelper
import javax.inject.Inject

class ShowNotificationUseCase @Inject constructor(
    private val notificationHelper: NotificationHelper
) {
    operator fun invoke(title: String, message: String) {
        Log.d("ShowNotificationUseCase", "Showing notification: $title, $message")
        notificationHelper.showNotification(title, message)
    }
}