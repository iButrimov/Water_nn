package com.example.water_nn.domain.usecases

import com.example.water_nn.domain.models.OrderData
import com.example.water_nn.domain.models.ValidationStatus

class ValidateNewOrderDataUseCase {
    fun execute(dataOrder: OrderData): List<ValidationStatus> {
        val validationStatusList = mutableListOf<ValidationStatus>()

        if (dataOrder.address.isBlank()) {
            validationStatusList.add(ValidationStatus.ADDRESS_FIELD_IS_EMPTY)
        }

        if (dataOrder.phoneNumber.isBlank()) {
            validationStatusList.add(ValidationStatus.PHONE_NUMBER_FIELD_IS_EMPTY)
        }

        if (validationStatusList.isEmpty()) {
            validationStatusList.add(ValidationStatus.SUCCESS)
        }

        return validationStatusList
    }
}