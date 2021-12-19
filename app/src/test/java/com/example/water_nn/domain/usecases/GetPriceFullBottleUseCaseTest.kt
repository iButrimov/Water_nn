package com.example.water_nn.domain.usecases

import com.example.water_nn.domain.repositories.IRepository
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetPriceFullBottleUseCaseTest {

    private val repository = mockk<IRepository.PriceRepository>()
    private lateinit var getPriceFullBottleUseCase: GetPriceFullBottleUseCase

    @Before
    fun setUp() {
        getPriceFullBottleUseCase = GetPriceFullBottleUseCase(repository)
    }

    @Test
    fun execute() {

        every { repository.getPriceFullBottle() } returns 180.0

        val priceFullBottle = getPriceFullBottleUseCase.execute()

        coVerify { repository.getPriceFullBottle() }

        assertEquals(180.0, priceFullBottle, 0.0)
    }
}