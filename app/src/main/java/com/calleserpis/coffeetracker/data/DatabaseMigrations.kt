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
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS achievements (
                id TEXT NOT NULL PRIMARY KEY,
                unlockedAt INTEGER,
                progress INTEGER NOT NULL DEFAULT 0
            )
        """)
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE coffee_table ADD COLUMN remoteId TEXT")
        db.execSQL(
            "ALTER TABLE coffee_table ADD COLUMN syncStatus TEXT NOT NULL DEFAULT 'PENDING_UPLOAD'"
        )
        db.execSQL(
            "ALTER TABLE coffee_table ADD COLUMN updatedAt INTEGER NOT NULL DEFAULT 0"
        )
        db.execSQL("UPDATE coffee_table SET updatedAt = timestamp WHERE updatedAt = 0")

        db.execSQL(
            "ALTER TABLE achievements ADD COLUMN syncStatus TEXT NOT NULL DEFAULT 'PENDING_UPLOAD'"
        )
        db.execSQL(
            "ALTER TABLE achievements ADD COLUMN updatedAt INTEGER NOT NULL DEFAULT 0"
        )
        db.execSQL(
            "UPDATE achievements SET updatedAt = COALESCE(unlockedAt, ${System.currentTimeMillis()}) WHERE updatedAt = 0"
        )
    }
}