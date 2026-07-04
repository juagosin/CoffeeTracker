package com.calleserpis.coffeetracker.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "coffee_preferences")

@Singleton
class CoffeePreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val LAST_COFFEE_KEY = stringPreferencesKey("last_coffee_type")
        private val CLOUD_MIGRATION_DONE_KEY =
            androidx.datastore.preferences.core.booleanPreferencesKey("cloud_migration_done")
    }

    val lastCoffeeType: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[LAST_COFFEE_KEY]
        }

    suspend fun saveLastCoffee(coffeeType: String) {
        context.dataStore.edit { preferences ->
            preferences[LAST_COFFEE_KEY] = coffeeType
        }
    }

    suspend fun clearLastCoffee() {
        context.dataStore.edit { preferences ->
            preferences.remove(LAST_COFFEE_KEY)
        }
    }

    suspend fun isCloudMigrationCompleted(): Boolean {
        return context.dataStore.data.map { it[CLOUD_MIGRATION_DONE_KEY] ?: false }.first()
    }

    suspend fun setCloudMigrationCompleted() {
        context.dataStore.edit { preferences ->
            preferences[CLOUD_MIGRATION_DONE_KEY] = true
        }
    }
}