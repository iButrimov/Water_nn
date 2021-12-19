package com.example.water_nn.domain.usecases

import com.example.water_nn.domain.models.AuthData
import com.example.water_nn.domain.repositories.IRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CreateNewUserUseCaseTest {

    private lateinit var createNewUserUseCase: CreateNewUserUseCase
    private val repository = mockk<IRepository.UserRepository>()

    @Before
    fun init() {
        createNewUserUseCase = CreateNewUserUseCase(repository)
    }

    @Test
    fun `create new user success`() = runBlocking {
        val authData = mockk<AuthData>()

        coEvery { repository.createNewUser(authData) } returns Unit

        createNewUserUseCase.execute(authData)

        coVerify { repository.createNewUser(authData) }
    }
}