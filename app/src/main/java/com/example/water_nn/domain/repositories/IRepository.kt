package com.example.water_nn.domain.repositories

import com.example.water_nn.data.database.entity.Order
import com.example.water_nn.domain.models.*
import kotlinx.coroutines.flow.Flow

interface IRepository {

    interface LocalRepository {
        suspend fun addOrder(order: Order)
        suspend fun deleteOrder(order: Order)
        suspend fun getOrderById(id: String): Order
        suspend fun getAllOrders(): Flow<List<Order>>
        suspend fun isNewOrderDataValid(dataOrder: OrderData): List<ValidationStatus>
    }

    interface UserRepository {
        suspend fun validationAuthDataList(authData: AuthData): List<AuthValidationStatus>
        suspend fun createNewUser(authData: AuthData)
        suspend fun checkUserCreated(): Boolean
        suspend fun getUserInfo(): UserInformation
        suspend fun saveUserInformation(userInformation: UserInformation)
    }

    interface PriceRepository {
        fun getPriceFullBottle(): Double
        fun getPriceEmptyBottle(): Double
    }
}