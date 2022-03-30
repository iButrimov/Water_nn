package com.example.water_nn.presentation.main.orders

import com.example.water_nn.data.database.entity.Order

interface OrderItem {
    val order: Order
}

class CompletedOrder(
    override val order: Order
) : OrderItem