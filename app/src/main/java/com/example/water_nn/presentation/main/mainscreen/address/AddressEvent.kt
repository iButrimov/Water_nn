package com.example.water_nn.presentation.main.mainscreen.address

import com.example.water_nn.data.database.entity.Address
import com.example.water_nn.presentation.OneShot

sealed class AddressEvent {
    class AddNewAddress(val address: String) : AddressEvent()
    class AddressSelected(val address: Address) : AddressEvent()
    object AddressListRequest : AddressEvent()
    object ConfirmSelectedAddress : AddressEvent()
}

sealed class AddressOneShot : OneShot {
    object CreateNewAddress : AddressOneShot()
}