package com.example.water_nn.domain.usecases

import com.example.water_nn.data.database.entity.Order
import com.example.water_nn.domain.repositories.IRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetOrderByIdUseCaseTest {

    private val repository = mockk<IRepository.OrderRepository>()
    private lateinit var getOrderByIdUseCase: GetOrderByIdUseCase

    @Before
    fun init() {
        getOrderByIdUseCase = GetOrderByIdUseCase(repository)
    }

    @Test
    fun `get order by id success`() = runBlocking {

        val id = "1"
        val order = mockk<Order>()

        coEvery { repository.getOrderById(id) } returns order
        getOrderByIdUseCase.invoke(id)
        coVerify { repository.getOrderById(id) }
    }
}