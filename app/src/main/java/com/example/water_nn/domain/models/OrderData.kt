package com.example.water_nn.domain.models

data class OrderData(
    val name: String?,
    val address: String,
    val phoneNumber: String,
    val quantityFullBottle: Int,
    val quantityEmptyBottle: Int,
//    @Embedded val shopOrder: List<AdditionalItem>,
    val deliveryDay: DeliveryDay,
    val deliveryTime: List<DeliveryTime>,
    val comment: String,
)
