package com.example.water_nn.domain.usecases

import com.example.water_nn.domain.repositories.IRepository

class CheckUserCreatedUseCase(private val repository: IRepository.UserRepository) {
    suspend fun execute(): Boolean = repository.checkUserCreated()
}