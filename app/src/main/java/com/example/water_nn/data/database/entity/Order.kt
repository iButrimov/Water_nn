package com.example.water_nn.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.water_nn.domain.modules.DeliveryDay
import com.example.water_nn.domain.modules.DeliveryTime

@Entity(tableName = "order_table")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "phoneNumber") val phoneNumber: String,
    @ColumnInfo(name = "quantityWater") val quantityWater: Int,
    @ColumnInfo(name = "quantityEmptyBottle") val quantityEmptyBottle: Int,
    @ColumnInfo(name = "waterPrice") val waterPrice: Double = 180.0,
    @ColumnInfo(name = "emptyBottlePrice") val emptyBottlePrice: Double = 200.0,
//    @Embedded val shopOrder: List<AdditionalItem>,
    @ColumnInfo(name = "deliveryDay") val deliveryDay: DeliveryDay,
    @ColumnInfo(name = "deliveryTime") val deliveryTime: DeliveryTime,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "totalPrice") val totalPrice: Double,
)

data class AdditionalItem(
    val id: Int,
    val name: String,
    val picture: String,
    val price: Double, //цена сохраняется старой
    val quantity: Int
)