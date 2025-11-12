package com.calleserpis.coffeetracker.data

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Añadir la columna price a la tabla existente
        db.execSQL("ALTER TABLE coffee_table ADD COLUMN price REAL")
    }
}