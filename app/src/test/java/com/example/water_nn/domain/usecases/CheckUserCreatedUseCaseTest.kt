package com.example.water_nn.domain.usecases

import com.example.water_nn.domain.repositories.IRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CheckUserCreatedUseCaseTest {

    private val repository = mockk<IRepository.UserRepository>()
    private lateinit var checkUserCreatedUseCase: CheckUserCreatedUseCase

    @Before
    fun init() {
        coEvery { repository.checkUserCreated() } returns true
        checkUserCreatedUseCase = CheckUserCreatedUseCase(repository)
    }

    @Test
    fun `check is user created`() = runBlocking {
        checkUserCreatedUseCase.execute()
        coVerify { repository.checkUserCreated() }
    }
}