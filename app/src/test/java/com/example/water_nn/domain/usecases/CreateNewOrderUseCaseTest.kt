package com.example.water_nn.domain.usecases

import com.example.water_nn.data.database.entity.Order
import com.example.water_nn.domain.repositories.IRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CreateNewOrderUseCaseTest {

    private val repository = mockk<IRepository.OrderRepository>()
    private lateinit var createNewOrderUseCase: CreateNewOrderUseCase

    @Before
    fun init() {
        createNewOrderUseCase = CreateNewOrderUseCase(repository)
    }

    @Test
    fun `create new order success`() = runBlocking {

        val newOrder = mockk<Order>()

        coEvery { repository.addOrder(newOrder) } returns Unit

        createNewOrderUseCase.execute(newOrder)

        coVerify {
            repository.addOrder(newOrder)
        }
    }
}