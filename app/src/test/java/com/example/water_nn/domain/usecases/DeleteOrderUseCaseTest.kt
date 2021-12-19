package com.example.water_nn.domain.usecases

import com.example.water_nn.data.database.entity.Order
import com.example.water_nn.domain.repositories.IRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteOrderUseCaseTest {

    private val repository = mockk<IRepository.OrderRepository>()
    private lateinit var deleteOrderUseCase: DeleteOrderUseCase

    @Before
    fun init() {
        deleteOrderUseCase = DeleteOrderUseCase(repository)
    }

    @Test
    fun `delete order success`() = runBlocking {
        val order = mockk<Order>()

        coEvery { repository.deleteOrder(order) } returns Unit

        deleteOrderUseCase.execute(order)

        coVerify { repository.deleteOrder(order) }

    }
}