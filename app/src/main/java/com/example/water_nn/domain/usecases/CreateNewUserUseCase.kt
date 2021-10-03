package com.example.water_nn.domain.usecases

import com.example.water_nn.domain.models.AuthData
import com.example.water_nn.domain.repositories.IRepository

class CreateNewUserUseCase(private val repository: IRepository.UserRepository) {
    suspend fun execute(authData: AuthData) = repository.createNewUser(authData)
}