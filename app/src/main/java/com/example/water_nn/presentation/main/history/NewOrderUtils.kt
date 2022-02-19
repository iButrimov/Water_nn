package com.example.water_nn.presentation.main.history

import com.example.water_nn.domain.models.DeliveryDay
import com.example.water_nn.domain.models.DeliveryTime

object NewOrderUtils {

    fun buildNewOrderItems(
        name: String?,
        address: String?,
        phoneNumber: String?,
        street: String?,
        building: String?,
        floor: String?,
        apartment: String?,
        priceFull: Double,
        priceEmpty: Double,
        qtyFull: Int,
        qtyEmpty: Int,
        deliveryDay: DeliveryDay,
        deliveryTimes: List<DeliveryTime>,
        comment: String?
    ): List<ItemOrderCard> {
        return mutableListOf<ItemOrderCard>().apply {
            add(buildOrderUserInfo(name, address, phoneNumber))
            add(buildAddressInfo(street, building, floor, apartment))
            add(buildOrderWaterInfo(priceFull, priceEmpty, qtyFull, qtyEmpty))
            add(buildDeliveryInfo(deliveryDay, deliveryTimes))
            add(buildCommentInfo(comment))
        }
    }

    private fun buildOrderUserInfo(
        name: String?,
        address: String?,
        phoneNumber: String?
    ): ItemOrderCard.OrderUserInfo {
        return ItemOrderCard.OrderUserInfo(
            name = name ?: "",
            phone = phoneNumber ?: ""
        )
    }

    private fun buildAddressInfo(
        street: String?,
        building: String?,
        floor: String?,
        apartment: String?
    ): ItemOrderCard.OrderAddressInfo {
        return ItemOrderCard.OrderAddressInfo(
            street = street ?: "",
            building = building ?: "",
            floor = floor ?: "",
            apartment = apartment ?: "",
        )
    }

    private fun buildOrderWaterInfo(
        priceFull: Double,
        priceEmpty: Double,
        qtyFull: Int,
        qtyEmpty: Int
    ): ItemOrderCard.OrderWaterInfo {
        return ItemOrderCard.OrderWaterInfo(
            priceFull = priceFull,
            priceEmpty = priceEmpty,
            qtyFull = qtyFull,
            qtyEmpty = qtyEmpty,
        )
    }

    private fun buildDeliveryInfo(
        deliveryDay: DeliveryDay,
        deliveryTimes: List<DeliveryTime>
    ): ItemOrderCard.OrderTimeInfo {
        return ItemOrderCard.OrderTimeInfo(
            deliveryDay = deliveryDay,
            deliveryTimes = deliveryTimes
        )
    }

    private fun buildCommentInfo(
        comment: String?
    ): ItemOrderCard.OrderCommentInfo {
        return ItemOrderCard.OrderCommentInfo(
            comment = comment ?: ""
        )
    }
}

