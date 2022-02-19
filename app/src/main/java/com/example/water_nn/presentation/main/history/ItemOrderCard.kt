package com.example.water_nn.presentation.main.history

import com.example.water_nn.domain.models.DeliveryDay
import com.example.water_nn.domain.models.DeliveryTime

sealed class ItemOrderCard {
    data class OrderUserInfo(
        val name: String,
        val phone: String
    ) : ItemOrderCard()

    data class OrderAddressInfo(
        val street: String,
        val building: String,
        val floor: String,
        val apartment: String
    ) : ItemOrderCard()

    data class OrderTimeInfo(
        val deliveryDay: DeliveryDay,
        val deliveryTimes: List<DeliveryTime>
    ) : ItemOrderCard()

    data class OrderWaterInfo(
        val priceFull: Double,
        val priceEmpty: Double,
        val qtyFull: Int,
        val qtyEmpty: Int
    ) : ItemOrderCard()

    data class OrderCommentInfo(
        val comment: String
    ) : ItemOrderCard()

}
