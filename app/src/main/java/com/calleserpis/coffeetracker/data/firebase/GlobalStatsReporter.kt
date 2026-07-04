package com.calleserpis.coffeetracker.data.firebase

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Estadísticas globales agregadas (sin datos personales).
 * Los documentos solo incrementan contadores; no se guarda uid ni historial individual aquí.
 */
@Singleton
class GlobalStatsReporter @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val analytics: FirebaseAnalytics
) {

    suspend fun reportCoffeeAdded(coffeeType: String) {
        runCatching { reportCoffeeAddedInternal(coffeeType) }
    }

    suspend fun reportAchievementUnlocked(achievementId: String) {
        runCatching { reportAchievementUnlockedInternal(achievementId) }
    }

    private suspend fun reportCoffeeAddedInternal(coffeeType: String) {
        val batch = firestore.batch()
        val totalsRef = firestore.collection(COL_GLOBAL_STATS).document(DOC_TOTALS)
        batch.set(
            totalsRef,
            mapOf(FIELD_TOTAL_COFFEES to FieldValue.increment(1)),
            SetOptions.merge()
        )
        val typeRef = firestore.collection(COL_GLOBAL_STATS)
            .document(DOC_COFFEE_TYPES)
            .collection(COL_TYPES)
            .document(coffeeType)
        batch.set(
            typeRef,
            mapOf(FIELD_COUNT to FieldValue.increment(1)),
            SetOptions.merge()
        )
        batch.commit().await()

        analytics.logEvent(
            EVENT_COFFEE_ADDED,
            Bundle().apply { putString(PARAM_COFFEE_TYPE, coffeeType) }
        )
    }

    private suspend fun reportAchievementUnlockedInternal(achievementId: String) {
        val achievementRef = firestore.collection(COL_GLOBAL_STATS)
            .document(DOC_ACHIEVEMENTS)
            .collection(COL_ITEMS)
            .document(achievementId)
        achievementRef.set(
            mapOf(FIELD_COUNT to FieldValue.increment(1)),
            SetOptions.merge()
        ).await()

        analytics.logEvent(
            EVENT_ACHIEVEMENT_UNLOCKED,
            Bundle().apply { putString(PARAM_ACHIEVEMENT_ID, achievementId) }
        )
    }

    companion object {
        const val COL_GLOBAL_STATS = "global_stats"
        const val DOC_TOTALS = "totals"
        const val DOC_COFFEE_TYPES = "coffee_types"
        const val DOC_ACHIEVEMENTS = "achievements"
        const val COL_TYPES = "types"
        const val COL_ITEMS = "items"
        const val FIELD_TOTAL_COFFEES = "totalCoffees"
        const val FIELD_COUNT = "count"

        const val EVENT_COFFEE_ADDED = "coffee_added_global"
        const val EVENT_ACHIEVEMENT_UNLOCKED = "achievement_unlocked_global"
        const val PARAM_COFFEE_TYPE = "coffee_type"
        const val PARAM_ACHIEVEMENT_ID = "achievement_id"
    }
}
