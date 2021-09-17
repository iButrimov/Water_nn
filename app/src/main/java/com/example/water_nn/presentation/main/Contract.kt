package com.example.water_nn.presentation.main

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.water_nn.data.database.entity.Order
import com.example.water_nn.domain.models.OrderData
import com.example.water_nn.domain.models.ValidationStatus

interface Contract {

    interface IAllOrdersViewModel {
        val order: LiveData<Order>
        val orderList: LiveData<List<Order>>

        fun deleteOrder(order: Order)
        fun getOrder(id: String)
        fun getAllOrders()
    }

    interface INewOrderViewModel {
        val validationStatusList: LiveData<List<ValidationStatus>>
        fun createOrder(orderData: OrderData)
        suspend fun isOrderDataValid (orderData: OrderData): Boolean
    }
}