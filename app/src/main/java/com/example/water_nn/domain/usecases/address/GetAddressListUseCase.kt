package com.example.water_nn.domain.usecases.address

import com.example.water_nn.data.database.entity.Address
import com.example.water_nn.domain.repositories.IRepository

class GetAddressListUseCase(
    private val repository: IRepository.AddressRepository
) : suspend () -> List<Address> {
    override suspend fun invoke(): List<Address> = repository.getAddressList()
}