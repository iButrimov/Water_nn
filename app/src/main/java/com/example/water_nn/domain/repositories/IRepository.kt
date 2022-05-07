package com.example.water_nn.domain.repositories

import com.example.water_nn.data.database.entity.Address
import com.example.water_nn.data.database.entity.Order
import com.example.water_nn.domain.models.AuthData
import com.example.water_nn.domain.models.UserInformation
import kotlinx.coroutines.flow.Flow

interface IRepository {

    interface OrderRepository {
        suspend fun addOrder(order: Order)
        suspend fun deleteOrder(order: Order)
        suspend fun getOrderById(id: String): Order
        suspend fun getAllOrders(): Flow<List<Order>>
    }

    interface UserRepository {
        suspend fun createNewUser(authData: AuthData)
        suspend fun checkUserCreated(): Boolean
        suspend fun getLocalUserInfo(): UserInformation
        suspend fun saveUserInformation(userInformation: UserInformation)
    }

    interface PriceRepository {
        fun getPriceFullBottle(): Double
        fun getPriceEmptyBottle(): Double
    }

    interface AddressRepository {
        suspend fun addAddress(address: Address)
        suspend fun confirmAddress()
        suspend fun deleteAddress(address: Address)
        suspend fun getAddressById(id: String): Address
        suspend fun getAddressList(): List<Address>
        suspend fun getAddressListBySelected(): List<Address>
        suspend fun getLastSavedAddress(): Address
        suspend fun saveSelectedAddress(address: Address)
        suspend fun changeSelectedAddress(address: Address)
    }
}