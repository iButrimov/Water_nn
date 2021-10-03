package com.example.water_nn.data.repositories

import android.content.SharedPreferences
import com.example.water_nn.domain.models.AuthData
import com.example.water_nn.domain.models.AuthValidationStatus
import com.example.water_nn.domain.repositories.IRepository

class UserRepository (private val sharedPref: SharedPreferences): IRepository.UserRepository {

    override suspend fun validationAuthDataList(authData: AuthData): List<AuthValidationStatus> {
        val validationAuthStatusList = mutableListOf<AuthValidationStatus>()

        if (authData.name.isBlank()) {
            validationAuthStatusList.add(AuthValidationStatus.NAME_FIELD_IS_EMPTY)
        }

        if (authData.address.isBlank()) {
            validationAuthStatusList.add(AuthValidationStatus.ADDRESS_FIELD_IS_EMPTY)
        }

        if (authData.phoneNumber.isBlank()) {
            validationAuthStatusList.add(AuthValidationStatus.PHONE_NUMBER_FIELD_IS_EMPTY)
        }

        if (validationAuthStatusList.isEmpty()) {
            validationAuthStatusList.add(AuthValidationStatus.SUCCESS)
        }

        return validationAuthStatusList
    }

    override suspend fun createNewUser(authData: AuthData) {
        sharedPref.edit().apply {
            putString(NAME_KEY, authData.name)
            putString(ADDRESS_KEY, authData.address)
            putString(PHONE_NUMBER_KEY, authData.phoneNumber)
            putBoolean(USER_IS_CREATED, true)
            apply()
        }
    }

    override suspend fun checkUserCreated(): Boolean {
        return sharedPref.getBoolean(USER_IS_CREATED, false)
    }

    companion object {
        private const val NAME_KEY = "NAME_KEY"
        private const val ADDRESS_KEY = "ADDRESS_KEY"
        private const val PHONE_NUMBER_KEY = "PHONE_NUMBER_KEY"
        private const val USER_IS_CREATED = "USER_IS_CREATED"
    }
}