package com.example.water_nn.domain.usecases

import com.example.water_nn.domain.models.UserInformation
import com.example.water_nn.domain.repositories.IRepository

class GetLocalUserInfoUseCase(
    private val userRepository: IRepository.UserRepository
) : suspend () -> UserInformation? {
    override suspend fun invoke(): UserInformation = userRepository.getLocalUserInfo()
}