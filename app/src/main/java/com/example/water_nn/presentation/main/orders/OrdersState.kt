package com.example.water_nn.presentation.main.orders

import com.example.water_nn.data.database.entity.Order

data class OrdersState(
    val orders: List<Order> = emptyList()
) {
    val haveNotOrders: Boolean = orders.isEmpty()
}