package com.example.water_nn.domain.usecases

import com.example.water_nn.domain.repositories.IRepository
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetPriceEmptyBottleUseCaseTest {

    private val repository = mockk<IRepository.PriceRepository>()
    private lateinit var getPriceEmptyBottleUseCase: GetPriceEmptyBottleUseCase

    @Before
    fun init() {
        getPriceEmptyBottleUseCase = GetPriceEmptyBottleUseCase(repository)
    }

    @Test
    fun `get price empty bottle success`() {

        every { repository.getPriceEmptyBottle() } returns 200.0

        val priceEmptyBottle = getPriceEmptyBottleUseCase.execute()

        coVerify { repository.getPriceEmptyBottle() }

        assertEquals(200.0, priceEmptyBottle, 0.0)
    }
}