package com.example.water_nn.domain.usecases.address

import com.example.water_nn.data.database.entity.Address
import com.example.water_nn.domain.repositories.IRepository

class AddNewAddressUseCase(
    private val repository: IRepository.AddressRepository
) : suspend (Address) -> Unit {
    override suspend fun invoke(address: Address) {
        repository.addAddress(address)
    }
}