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

class GetLocalUserInfoUseCaseTest {

    private val repository = mockk<IRepository.UserRepository>()
    private lateinit var getLocalUserInfoUseCase: GetLocalUserInfoUseCase


    @Before
    fun init() {
        getLocalUserInfoUseCase = GetLocalUserInfoUseCase(repository)
    }

    @Test
    fun `get user info success`() = runBlocking {

        val userInfoFromRepo = mockk<UserInformation>()

        coEvery { repository.getLocalUserInfo() } returns userInfoFromRepo

        val userInformation = getLocalUserInfoUseCase.invoke()

        coVerify { repository.getLocalUserInfo() }

        assertEquals(userInfoFromRepo, userInformation)
    }
}