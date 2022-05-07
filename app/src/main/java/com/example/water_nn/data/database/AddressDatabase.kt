package com.example.water_nn.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.water_nn.data.database.dao.AddressDao
import com.example.water_nn.data.database.entity.Address

@Database(entities = [Address::class], version = 1, exportSchema = false)
abstract class AddressDatabase : RoomDatabase() {
    abstract fun getAddressDao(): AddressDao
}