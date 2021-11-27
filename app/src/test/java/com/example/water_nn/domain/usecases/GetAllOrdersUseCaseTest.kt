package com.example.water_nn.domain.usecases

import com.example.water_nn.domain.repositories.IRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetAllOrdersUseCaseTest {

    private val repository = mockk<IRepository.LocalRepository>()
    private lateinit var getAllOrdersUseCase: GetAllOrdersUseCase

    @Before
    fun before() {
        getAllOrdersUseCase = GetAllOrdersUseCase(repository)
    }

    @Test
    fun `get all orders success`() = runBlocking {
        getAllOrdersUseCase.execute()
        coVerify {
            repository.getAllOrders()
        }
    }
}