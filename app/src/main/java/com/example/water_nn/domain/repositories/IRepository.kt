package com.example.water_nn.domain.repositories

import android.content.SharedPreferences
import com.example.water_nn.data.database.entity.Order
import com.example.water_nn.domain.models.AuthData
import com.example.water_nn.domain.models.AuthValidationStatus
import com.example.water_nn.domain.models.OrderData
import com.example.water_nn.domain.models.ValidationStatus
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
    }

    interface PriceRepository {
        fun getPriceFullBottle(): Double
        fun getPriceEmptyBottle(): Double
    }
}