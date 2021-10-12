package com.example.water_nn.domain.usecases

import com.example.water_nn.domain.models.UserInformation
import com.example.water_nn.domain.repositories.IRepository

class SaveUserInformationUseCase(private val repository: IRepository.UserRepository) {
    suspend fun execute(userInformation: UserInformation) =
        repository.saveUserInformation(userInformation)
}