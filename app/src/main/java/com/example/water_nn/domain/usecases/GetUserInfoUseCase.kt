package com.example.water_nn.domain.usecases

import com.example.water_nn.domain.models.UserInformation
import com.example.water_nn.domain.repositories.IRepository

class GetUserInfoUseCase(private val repository: IRepository.UserRepository) {
    suspend fun execute(): UserInformation = repository.getUserInfo()
}