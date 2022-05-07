package com.example.water_nn.domain.usecases.address

import com.example.water_nn.domain.repositories.IRepository

class ConfirmSelectedAddressUseCse(
    private val repository: IRepository.AddressRepository
) : suspend () -> Unit {
    override suspend fun invoke() {
        repository.confirmAddress()
    }
}