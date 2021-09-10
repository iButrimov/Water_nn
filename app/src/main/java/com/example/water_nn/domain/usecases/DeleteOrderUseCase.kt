package com.example.water_nn.domain.usecases

import com.example.water_nn.data.database.entity.Order
import com.example.water_nn.domain.repositories.IRepository

class DeleteOrderUseCase(private val repository: IRepository.LocalRepository) {
    suspend fun execute(order: Order) = repository.deleteOrder(order)
}