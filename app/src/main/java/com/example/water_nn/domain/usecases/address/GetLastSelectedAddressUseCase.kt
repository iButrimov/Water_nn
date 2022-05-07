package com.example.water_nn.domain.usecases.address

import com.example.water_nn.data.database.entity.Address
import com.example.water_nn.domain.repositories.IRepository

class GetLastSelectedAddressUseCase(
    private val repository: IRepository.AddressRepository
) : suspend () -> Address {
    override suspend fun invoke(): Address {
        return repository.getLastSavedAddress()
    }
}