package com.example.water_nn.data.repositories

import com.example.water_nn.data.database.AppDatabase
import com.example.water_nn.data.database.entity.Order
import com.example.water_nn.domain.repositories.IRepository
import kotlinx.coroutines.flow.Flow

class OrderRepository(private val dataBase: AppDatabase) : IRepository.LocalRepository {

    override suspend fun addOrder(order: Order) {
        dataBase.getOrderDao().addOrder(order)
    }

    override suspend fun deleteOrder(order: Order) {
        dataBase.getOrderDao().delete(order)
    }

    override suspend fun getOrder(id: String): Order {
        return dataBase.getOrderDao().getOrderById(id)
    }

    override suspend fun getAllOrders(): Flow<List<Order>> {
        return dataBase.getOrderDao().getAllOrders()
    }


}