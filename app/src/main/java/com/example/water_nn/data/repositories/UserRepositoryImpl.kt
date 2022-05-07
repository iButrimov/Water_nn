package com.example.water_nn.data.repositories

import android.content.SharedPreferences
import com.example.water_nn.domain.models.AuthData
import com.example.water_nn.domain.models.UserInformation
import com.example.water_nn.domain.repositories.IRepository
import com.example.water_nn.presentation.extentions.getStringOrEmpty

class UserRepositoryImpl(private val sharedPref: SharedPreferences) : IRepository.UserRepository {

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

    override suspend fun getLocalUserInfo(): UserInformation {

        val name = sharedPref.getStringOrEmpty(NAME_KEY)
        val email = sharedPref.getStringOrEmpty(EMAIL_KEY)
        val phone = sharedPref.getStringOrEmpty(PHONE_NUMBER_KEY)

        return UserInformation(name, email, phone)
    }

    override suspend fun saveUserInformation(userInformation: UserInformation) {
        sharedPref.edit().apply {
            putString(NAME_KEY, userInformation.name)
            putString(EMAIL_KEY, userInformation.email)
            putString(PHONE_NUMBER_KEY, userInformation.phone)
            apply()
        }
    }

    companion object {
        const val NAME_KEY = "NAME_KEY"
        const val EMAIL_KEY = "EMAIL_KEY"
        const val PHONE_NUMBER_KEY = "PHONE_NUMBER_KEY"
        const val ADDRESS_KEY = "ADDRESS_KEY"
        const val BUILDING_NUMBER_KEY = "BUILDING_NUMBER_KEY"
        const val FLOOR_NUMBER_KEY = "FLOOR_NUMBER_KEY"
        const val APARTMENT_NUMBER_KEY = "APARTMENT_NUMBER_KEY"
        const val USER_IS_CREATED = "USER_IS_CREATED"
    }
}