package com.calleserpis.coffeetracker.data

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Añadir la columna price a la tabla existente
        db.execSQL("ALTER TABLE coffee_table ADD COLUMN price REAL")
    }
}
val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Añadir la columna price a la tabla existente
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS achievements (
                id TEXT NOT NULL PRIMARY KEY,
                unlockedAt INTEGER NOT NULL,
                progress INTEGER NOT NULL DEFAULT 0
            )
        """)
    }
}