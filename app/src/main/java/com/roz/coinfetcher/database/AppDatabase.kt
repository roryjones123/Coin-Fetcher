package com.roz.coinfetcher.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.roz.coinfetcher.basicfeature.data.local.dao.CoinDao
import com.roz.coinfetcher.basicfeature.data.local.model.CoinCached

private const val DATABASE_VERSION = 1

@Database(
    entities = [CoinCached::class],
    version = DATABASE_VERSION,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao
}
