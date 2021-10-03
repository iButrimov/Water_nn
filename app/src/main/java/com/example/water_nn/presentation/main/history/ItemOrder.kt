package com.example.water_nn.presentation.main.history

import com.example.water_nn.data.database.entity.Order

sealed class ItemOrder {
    data class CompletedOrder(val order: Order): ItemOrder()
//    data class CurrentOrder(val orderTime: Long) :ItemOrder()
}