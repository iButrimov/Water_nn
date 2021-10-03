package com.example.water_nn.domain.usecases

import com.example.water_nn.domain.repositories.IRepository

class GetPriceFullBottleUseCase(private val repository: IRepository.PriceRepository) {
    fun execute(): Double = repository.getPriceFullBottle()
}