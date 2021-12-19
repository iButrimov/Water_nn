package com.example.water_nn.domain.usecases

import com.example.water_nn.domain.models.UserInformation
import com.example.water_nn.domain.repositories.IRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SaveUserInformationUseCaseTest {

    private val repository = mockk<IRepository.UserRepository>()
    private lateinit var saveUserInformationUseCase: SaveUserInformationUseCase

    @Before
    fun init() {
        saveUserInformationUseCase = SaveUserInformationUseCase(repository)
    }

    @Test
    fun `save user information success`() = runBlocking {

        val userInformation = mockk<UserInformation>()

        coEvery { repository.saveUserInformation(userInformation) } returns Unit

        saveUserInformationUseCase.execute(userInformation)

        coVerify { repository.saveUserInformation(userInformation) }
    }
}