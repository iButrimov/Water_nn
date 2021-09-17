package com.example.water_nn.presentation.main.history

sealed class ItemOrder {
    data class completedOrder(val orderTime: Long): ItemOrder()
//    data class currentOrder(val orderTime: Long) :ItemOrder()
}
