package com.example.water_nn.domain.usecases

import com.example.water_nn.domain.models.AuthData
import com.example.water_nn.domain.models.AuthValidationStatus
import com.example.water_nn.domain.repositories.IRepository

class ValidateAuthDataUseCase {
    fun execute(authData: AuthData): List<AuthValidationStatus> {
        val validationAuthStatusList = mutableListOf<AuthValidationStatus>()

        if (authData.name.isBlank()) {
            validationAuthStatusList.add(AuthValidationStatus.NAME_FIELD_IS_EMPTY)
        }

        if (authData.phoneNumber.isBlank()) {
            validationAuthStatusList.add(AuthValidationStatus.PHONE_NUMBER_FIELD_IS_EMPTY)
        }

        if (validationAuthStatusList.isEmpty()) {
            validationAuthStatusList.add(AuthValidationStatus.SUCCESS)
        }

        return validationAuthStatusList
    }

}