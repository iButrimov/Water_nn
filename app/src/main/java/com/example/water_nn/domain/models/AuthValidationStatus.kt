package com.example.water_nn.domain.models

enum class AuthValidationStatus {
    SUCCESS,
    NAME_FIELD_IS_EMPTY,
    ADDRESS_FIELD_IS_EMPTY,
    PHONE_NUMBER_FIELD_IS_EMPTY,
}