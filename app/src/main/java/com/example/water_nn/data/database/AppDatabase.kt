package com.example.water_nn.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.water_nn.data.database.dao.OrderDao
import com.example.water_nn.data.database.entity.Order

@Database(entities = [Order::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getOrderDao(): OrderDao
}