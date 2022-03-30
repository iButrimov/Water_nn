package com.example.water_nn.data.database.entity

import androidx.room.*
import com.example.water_nn.domain.models.DeliveryDay
import com.example.water_nn.domain.models.DeliveryTime

@Entity(tableName = "order_table")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "phoneNumber") val phoneNumber: String,
    @ColumnInfo(name = "street") val street: String,
    @ColumnInfo(name = "building") val building: String,
    @ColumnInfo(name = "floor") val floor: String,
    @ColumnInfo(name = "apartment") val apartment: String,
    @ColumnInfo(name = "quantityWater") val quantityWater: Int,
    @ColumnInfo(name = "quantityEmptyBottle") val quantityEmptyBottle: Int,
    @ColumnInfo(name = "waterPrice") val waterPrice: Double = 180.0,
    @ColumnInfo(name = "emptyBottlePrice") val emptyBottlePrice: Double = 200.0,
    @ColumnInfo(name = "deliveryDay") val deliveryDay: DeliveryDay,
    @ColumnInfo(name = "deliveryTime") val deliveryTime: List<DeliveryTime>,
    @ColumnInfo(name = "description") val comment: String,
    @ColumnInfo(name = "totalPrice") val totalPrice: Int,
)