package com.example.water_nn.data.repositories

import android.content.SharedPreferences
import com.example.water_nn.domain.models.AuthData
import com.example.water_nn.domain.models.AuthValidationStatus
import com.example.water_nn.domain.models.UserInformation
import com.example.water_nn.domain.repositories.IRepository
import com.example.water_nn.presentation.extentions.getStringOrEmpty

class UserRepository(private val sharedPref: SharedPreferences) : IRepository.UserRepository {

    override suspend fun createNewUser(authData: AuthData) {
        sharedPref.edit().apply {
            putString(NAME_KEY, authData.name)
            putString(PHONE_NUMBER_KEY, authData.phoneNumber)
            putBoolean(USER_IS_CREATED, true)
            apply()
        }
    }

    override suspend fun checkUserCreated(): Boolean {
        return sharedPref.getBoolean(USER_IS_CREATED, false)
    }

    override suspend fun getUserInfo(): UserInformation {

        val name = sharedPref.getStringOrEmpty(NAME_KEY)
        val phoneNumber = sharedPref.getStringOrEmpty(PHONE_NUMBER_KEY)
        val address = sharedPref.getStringOrEmpty(ADDRESS_KEY)
        val buildingNumber = sharedPref.getStringOrEmpty(BUILDING_NUMBER_KEY)
        val floorNumber = sharedPref.getStringOrEmpty(FLOOR_NUMBER_KEY)
        val apartmentNumber = sharedPref.getStringOrEmpty(APARTMENT_NUMBER_KEY)

        return UserInformation(
            name,
            phoneNumber,
            address,
            buildingNumber,
            floorNumber,
            apartmentNumber
        )
    }

    override suspend fun saveUserInformation(userInformation: UserInformation) {
        sharedPref.edit().apply {
            putString(NAME_KEY, userInformation.name)
            putString(PHONE_NUMBER_KEY, userInformation.phoneNumber)
            putString(ADDRESS_KEY, userInformation.address)
            putString(BUILDING_NUMBER_KEY, userInformation.buildingNumber)
            putString(FLOOR_NUMBER_KEY, userInformation.floorNumber)
            putString(APARTMENT_NUMBER_KEY, userInformation.apartmentNumber)
            apply()
        }
    }

    companion object {
        private const val NAME_KEY = "NAME_KEY"
        private const val PHONE_NUMBER_KEY = "PHONE_NUMBER_KEY"
        private const val ADDRESS_KEY = "ADDRESS_KEY"
        private const val BUILDING_NUMBER_KEY = "BUILDING_NUMBER_KEY"
        private const val FLOOR_NUMBER_KEY = "FLOOR_NUMBER_KEY"
        private const val APARTMENT_NUMBER_KEY = "APARTMENT_NUMBER_KEY"
        private const val USER_IS_CREATED = "USER_IS_CREATED"
    }
}