package com.example.water_nn.presentation.main.mainscreen.address

import com.example.water_nn.data.database.entity.Address
import com.example.water_nn.domain.usecases.address.*
import com.example.water_nn.presentation.BaseViewModel
import com.example.water_nn.presentation.main.mainscreen.address.AddressState as State
import com.example.water_nn.presentation.main.mainscreen.address.AddressEvent as Event

class AddressViewModel(
    private val addNewAddressUseCase: AddNewAddressUseCase,
    private val changeSelectedAddressUseCase: ChangeSelectedAddressUseCase,
    private val confirmSelectedAddressUseCse: ConfirmSelectedAddressUseCse,
    private val getAddressListBySelectedUseCase: GetAddressListBySelectedUseCase,
    private val getAddressListUseCase: GetAddressListUseCase
) : BaseViewModel<State, Event>(State()) {

    override suspend fun handleEvent(event: Event) {
        when (event) {
            is Event.AddNewAddress -> addNewAddress(event.address)
            is Event.AddressSelected -> handleAddressItemSelected(event.address)
            is Event.AddressListRequest -> getAddressListBySelected()
            is Event.ConfirmSelectedAddress -> confirmAddress()
        }
    }

    private fun handleAddressItemSelected(address: Address) {
        runSeparate {
            changeSelectedAddressUseCase(address)
            newState(State(getAddressListUseCase()))
        }
    }

    private fun getAddressListBySelected() {
        runSeparate {
            newState(State(getAddressListBySelectedUseCase()))
        }
    }

    private fun addNewAddress(address: String) {
        runSeparate {
            addNewAddressUseCase.invoke(
                Address(
                    id = 0,
                    address = address,
                    isSelected = false
                )
            )
            newState(State(getAddressListBySelectedUseCase()))
        }
    }

    private fun confirmAddress() {
        runSeparate {
            confirmSelectedAddressUseCse()
        }
    }
}