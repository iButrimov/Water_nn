package com.example.water_nn.domain.repositories

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
}