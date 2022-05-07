package com.example.water_nn.presentation.main.mainscreen.address

import com.example.water_nn.data.database.entity.Address

data class AddressState(
    val addressList: List<Address> = emptyList()
)