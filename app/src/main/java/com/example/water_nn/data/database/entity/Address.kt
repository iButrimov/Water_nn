package com.example.water_nn.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address_table")
data class Address(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "is_Selected") val isSelected: Boolean
)