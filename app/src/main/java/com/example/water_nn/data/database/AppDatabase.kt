package com.example.water_nn.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.water_nn.data.database.dao.OrderDao
import com.example.water_nn.data.database.entity.DeliveryTimeConverters
import com.example.water_nn.data.database.entity.Order

@Database(entities = [Order::class], version = 1, exportSchema = false)
@TypeConverters(DeliveryTimeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getOrderDao(): OrderDao
}