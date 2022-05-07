package com.example.water_nn.data.repositories

import com.example.water_nn.domain.repositories.IRepository

class PriceRepositoryImpl() : IRepository.PriceRepository {

    private val priceFullBottle: Double = 180.0
    private val priceEmptyBottle: Double = 200.0

    override fun getPriceFullBottle(): Double {
        return priceFullBottle
    }

    override fun getPriceEmptyBottle(): Double {
        return priceEmptyBottle
    }
}