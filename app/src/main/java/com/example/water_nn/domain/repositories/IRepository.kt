package com.example.water_nn.domain.repositories

import com.example.water_nn.data.database.entity.Order
import kotlinx.coroutines.flow.Flow

interface IRepository {

    interface LocalRepository {
        suspend fun addOrder (order: Order)
        suspend fun deleteOrder (order: Order)
        suspend fun getOrder (id: String) : Order
        suspend fun getAllOrders () : Flow<List<Order>>
    }
}