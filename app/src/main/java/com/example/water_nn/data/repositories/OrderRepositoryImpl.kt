package com.example.water_nn.data.repositories

import com.example.water_nn.data.database.AppDatabase
import com.example.water_nn.data.database.entity.Order
import com.example.water_nn.domain.repositories.IRepository
import kotlinx.coroutines.flow.Flow

class OrderRepositoryImpl(private val dataBase: AppDatabase) :
    IRepository.OrderRepository {

    override suspend fun addOrder(order: Order) {
        dataBase.getOrderDao().addOrder(order)
    }

    override suspend fun deleteOrder(order: Order) {
        dataBase.getOrderDao().deleteOrder(order)
    }

    override suspend fun getOrderById(id: String): Order {
        return dataBase.getOrderDao().getOrderById(id)
    }

    override suspend fun getAllOrders(): Flow<List<Order>> {
        return dataBase.getOrderDao().getAllOrders()
    }
}