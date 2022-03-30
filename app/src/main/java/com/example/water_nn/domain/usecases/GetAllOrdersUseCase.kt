package com.example.water_nn.domain.usecases

import com.example.water_nn.data.database.entity.Order
import com.example.water_nn.domain.repositories.IRepository
import kotlinx.coroutines.flow.Flow

class GetAllOrdersUseCase(
    private val repository: IRepository.OrderRepository
) : suspend () -> Flow<List<Order>> {
    override suspend fun invoke(): Flow<List<Order>> = repository.getAllOrders()
}