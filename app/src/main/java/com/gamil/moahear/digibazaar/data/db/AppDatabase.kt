package com.gamil.moahear.digibazaar.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gamil.moahear.digibazaar.data.model.ProductsResponse


@Database(entities = [ProductsResponse.Product::class], version = 1, exportSchema = false)
abstract class AppDatabase:RoomDatabase() {
    abstract fun getDao():ProductDao
}
