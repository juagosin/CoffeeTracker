package com.calleserpis.coffeetracker

import android.app.Application
import com.calleserpis.coffeetracker.data.sync.CoffeeSyncManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class CoffeeTracker : Application() {

    @Inject
    lateinit var coffeeSyncManager: CoffeeSyncManager

    override fun onCreate() {
        super.onCreate()
        coffeeSyncManager.initialize()
    }
}
