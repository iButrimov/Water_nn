package com.example.water_nn.domain.usecases

import com.example.water_nn.data.database.entity.Order
import com.example.water_nn.domain.models.OrderData
import com.example.water_nn.domain.repositories.IRepository

class CreateNewOrderUseCase(private val repository: IRepository.LocalRepository) {
    suspend fun execute(order: Order) = repository.addOrder(order)
}