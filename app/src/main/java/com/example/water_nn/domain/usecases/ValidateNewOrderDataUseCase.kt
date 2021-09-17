package com.example.water_nn.domain.usecases

import android.content.Context
import com.example.water_nn.domain.models.OrderData
import com.example.water_nn.domain.models.ValidationStatus
import com.example.water_nn.domain.repositories.IRepository

class ValidateNewOrderDataUseCase(private val repository: IRepository.LocalRepository) {
    suspend fun execute(dataOrder: OrderData): List<ValidationStatus> = repository.isNewOrderDataValid(dataOrder)
}