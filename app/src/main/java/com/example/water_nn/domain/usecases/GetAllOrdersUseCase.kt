package com.example.water_nn.domain.usecases

import com.example.water_nn.domain.repositories.IRepository

class GetAllOrdersUseCase (private val repository: IRepository.OrderRepository) {
    suspend fun execute() = repository.getAllOrders()
}