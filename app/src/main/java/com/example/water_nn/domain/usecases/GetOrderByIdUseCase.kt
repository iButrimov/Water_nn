package com.example.water_nn.domain.usecases

import com.example.water_nn.data.database.entity.Order
import com.example.water_nn.domain.repositories.IRepository

class GetOrderByIdUseCase(private val repository: IRepository.OrderRepository) {
    suspend fun execute(id: String): Order = repository.getOrderById(id)
}