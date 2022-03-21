package com.example.water_nn.data.repositories

import android.content.SharedPreferences
import com.example.water_nn.domain.models.AuthData
import com.example.water_nn.domain.models.UserInformation
import com.example.water_nn.presentation.extentions.getStringOrEmpty
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserRepositoryTest {

    private lateinit var repository: UserRepository
    private val sharedPreferences = mockk<SharedPreferences>()

    @Before
    fun init() {
        repository = UserRepository(sharedPreferences)
    }

    @Test
    fun `check user created returns true`() = runBlocking {

        coEvery { sharedPreferences.getBoolean(UserRepository.USER_IS_CREATED, false) } returns true

        val result = repository.checkUserCreated()

        coVerify { sharedPreferences.getBoolean(UserRepository.USER_IS_CREATED, false) }

        assertEquals(result, true)
    }

    @Test
    fun `create new user success`() = runBlocking {

        val authData = mockk<AuthData>()
        val editor = mockk<SharedPreferences.Editor>()

        coEvery { sharedPreferences.edit().apply() } returns Unit
        coEvery { authData.name } returns "Ivan"
        coEvery { authData.phoneNumber } returns "Phone"
        coEvery { sharedPreferences.edit().putString(UserRepository.NAME_KEY, "Ivan") } returns editor
        coEvery { sharedPreferences.edit().putString(UserRepository.PHONE_NUMBER_KEY, "Phone") } returns editor
        coEvery { sharedPreferences.edit().putBoolean(UserRepository.USER_IS_CREATED, true) } returns editor

        repository.createNewUser(authData)

        coVerify { sharedPreferences.edit().apply() }

    }

    @Test
    fun `get user info success`() = runBlocking {

        coEvery { sharedPreferences.getStringOrEmpty(UserRepository.NAME_KEY) } returns "Ivan"
        coEvery { sharedPreferences.getStringOrEmpty(UserRepository.PHONE_NUMBER_KEY) } returns "Phone"
        coEvery { sharedPreferences.getStringOrEmpty(UserRepository.ADDRESS_KEY) } returns "Address"
        coEvery { sharedPreferences.getStringOrEmpty(UserRepository.BUILDING_NUMBER_KEY) } returns "Building"
        coEvery { sharedPreferences.getStringOrEmpty(UserRepository.FLOOR_NUMBER_KEY) } returns "Floor"
        coEvery { sharedPreferences.getStringOrEmpty(UserRepository.APARTMENT_NUMBER_KEY) } returns "Apartment"

        val result = repository.getLocalUserInfo()

        coVerify { sharedPreferences.getStringOrEmpty(UserRepository.NAME_KEY) }
        coVerify { sharedPreferences.getStringOrEmpty(UserRepository.PHONE_NUMBER_KEY) }
        coVerify { sharedPreferences.getStringOrEmpty(UserRepository.ADDRESS_KEY) }
        coVerify { sharedPreferences.getStringOrEmpty(UserRepository.BUILDING_NUMBER_KEY) }
        coVerify { sharedPreferences.getStringOrEmpty(UserRepository.FLOOR_NUMBER_KEY) }
        coVerify { sharedPreferences.getStringOrEmpty(UserRepository.APARTMENT_NUMBER_KEY) }

        assertEquals(
            result, UserInformation(
                "Ivan",
                "Phone",
                "Address"
            )
        )
    }

    @Test
    fun `save user information success`() = runBlocking {

        val userInformation = mockk<UserInformation>()
        val editor = mockk<SharedPreferences.Editor>()

        coEvery { sharedPreferences.edit().apply() } returns Unit
        coEvery { userInformation.name } returns "Ivan"
        coEvery { userInformation.email } returns "email@email.com"
        coEvery { userInformation.phone } returns "Phone"

        coEvery { sharedPreferences.edit().putString(UserRepository.NAME_KEY, "Ivan") } returns editor
        coEvery { sharedPreferences.edit().putString(UserRepository.EMAIL_KEY, "email@email.com") } returns editor
        coEvery { sharedPreferences.edit().putString(UserRepository.PHONE_NUMBER_KEY, "Phone") } returns editor

        repository.saveUserInformation(userInformation)

        coVerify { sharedPreferences.edit().apply() }
    }
}