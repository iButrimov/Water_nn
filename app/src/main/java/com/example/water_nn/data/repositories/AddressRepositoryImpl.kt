package com.example.water_nn.data.repositories

import android.content.SharedPreferences
import com.example.water_nn.data.database.AddressDatabase
import com.example.water_nn.data.database.entity.Address
import com.example.water_nn.domain.repositories.IRepository
import com.example.water_nn.presentation.extentions.getBooleanOrFalse
import com.example.water_nn.presentation.extentions.getStringOrEmpty

class AddressRepositoryImpl(
    private val sharedPref: SharedPreferences,
    private val dataBase: AddressDatabase
) : IRepository.AddressRepository {

    private var currentList = mutableListOf<Address>()
    private lateinit var currentSelectedAddress: Address

    override suspend fun addAddress(address: Address) {
        dataBase.getAddressDao().addAddress(address)
    }

    override suspend fun changeSelectedAddress(address: Address) {
        currentList.forEachIndexed { index, element ->
            if (element == currentSelectedAddress) {
                currentList[index] = currentSelectedAddress.copy(isSelected = false)
            }
            if (element == address) {
                currentList[index] = address.copy(isSelected = true)
            }
        }

        currentSelectedAddress = address.copy(isSelected = true)
    }

    override suspend fun confirmAddress() {
        val lastSavedAddress = getLastSavedAddress()

        disableAddress(lastSavedAddress)
        saveSelectedAddress(currentSelectedAddress)
        enableAddress(currentSelectedAddress)
    }

    override suspend fun deleteAddress(address: Address) {
        dataBase.getAddressDao().deleteAddress(address)
    }

    override suspend fun getAddressById(id: String): Address {
        return dataBase.getAddressDao().getAddressById(id)
    }

    override suspend fun getAddressList(): List<Address> {
        return currentList.toList()
    }

    override suspend fun getAddressListBySelected(): List<Address> {
        val list = dataBase.getAddressDao().getAddressListBySelected()
        currentSelectedAddress = getLastSavedAddress()
        currentList = list.toMutableList()
        return list
    }

    override suspend fun getLastSavedAddress(): Address {
        val id = sharedPref.getInt(ADDRESS_ID_KEY, 0)
        val address = sharedPref.getStringOrEmpty(ADDRESS_VALUE_KEY)
        val isSelected = sharedPref.getBooleanOrFalse(ADDRESS_IS_SELECTED_KEY)
        return Address(id, address, isSelected)
    }

    override suspend fun saveSelectedAddress(address: Address) {
        sharedPref.edit().apply {
            putInt(ADDRESS_ID_KEY, address.id)
            putString(ADDRESS_VALUE_KEY, address.address)
            putBoolean(ADDRESS_IS_SELECTED_KEY, true)
            apply()
        }
    }

    private fun enableAddress(address: Address) {
        dataBase.getAddressDao().updateAddress(
            address.copy(
                isSelected = true
            )
        )
    }

    private fun disableAddress(address: Address) {
        dataBase.getAddressDao().updateAddress(
            address.copy(
                isSelected = false
            )
        )
    }

    companion object {
        const val ADDRESS_ID_KEY = "ADDRESS_ID_KEY"
        const val ADDRESS_VALUE_KEY = "ADDRESS_VALUE_KEY"
        const val ADDRESS_IS_SELECTED_KEY = "ADDRESS_IS_SELECTED_KEY"
    }
}