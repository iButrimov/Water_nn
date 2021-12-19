package com.example.water_nn.domain.usecases

import com.example.water_nn.domain.models.UserInformation
import com.example.water_nn.domain.repositories.IRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetUserInfoUseCaseTest {

    private val repository = mockk<IRepository.UserRepository>()
    private lateinit var getUserInfoUseCase: GetUserInfoUseCase


    @Before
    fun init() {
        getUserInfoUseCase = GetUserInfoUseCase(repository)
    }

    @Test
    fun `get user info success`() = runBlocking {

        val userInfoFromRepo = mockk<UserInformation>()

        coEvery { repository.getUserInfo() } returns userInfoFromRepo

        val userInformation = getUserInfoUseCase.execute()

        coVerify { repository.getUserInfo() }

        assertEquals(userInfoFromRepo, userInformation)
    }
}