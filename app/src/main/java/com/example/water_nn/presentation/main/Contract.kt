package com.example.water_nn.presentation.main

import androidx.lifecycle.LiveData
import com.example.water_nn.data.database.entity.Order

interface Contract {

    interface IOrderViewModel {
        val orderList: LiveData<List<Order>>

        suspend fun addOrder(order: Order)
        suspend fun deleteOrder(order: Order)
        suspend fun getOrder(id: String): Order
        fun getAllOrders()
    }
}