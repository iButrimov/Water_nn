package com.example.water_nn.data.repositories

import com.example.water_nn.data.database.AppDatabase
import com.example.water_nn.data.database.entity.Order
import com.example.water_nn.domain.models.AuthData
import com.example.water_nn.domain.models.AuthValidationStatus
import com.example.water_nn.domain.models.OrderData
import com.example.water_nn.domain.models.ValidationStatus
import com.example.water_nn.domain.repositories.IRepository
import kotlinx.coroutines.flow.Flow

class OrderRepository(private val dataBase: AppDatabase) : IRepository.LocalRepository {

    override suspend fun addOrder(order: Order) {
        dataBase.getOrderDao().addOrder(order)
    }

    override suspend fun deleteOrder(order: Order) {
        dataBase.getOrderDao().deleteOrder(order)
    }

    override suspend fun getOrderById(id: String): Order {
        return dataBase.getOrderDao().getOrderById(id)
    }

    override suspend fun getAllOrders(): Flow<List<Order>> {
        return dataBase.getOrderDao().getAllOrders()
    }

    override suspend fun isNewOrderDataValid(dataOrder: OrderData): List<ValidationStatus> {

        val validationStatusList = mutableListOf<ValidationStatus>()

        if (dataOrder.address.isBlank()) {
            validationStatusList.add(ValidationStatus.ADDRESS_FIELD_IS_EMPTY)
        }

        if (dataOrder.phoneNumber.isBlank()) {
            validationStatusList.add(ValidationStatus.PHONE_NUMBER_FIELD_IS_EMPTY)
        }

        if (validationStatusList.isEmpty()) {
            validationStatusList.add(ValidationStatus.SUCCESS)
        }

        return validationStatusList
    }
}