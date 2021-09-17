package com.example.water_nn.presentation.main.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.water_nn.data.database.entity.Order
import com.example.water_nn.domain.models.OrderData
import com.example.water_nn.domain.models.ValidationStatus
import com.example.water_nn.domain.usecases.CreateNewOrderUseCase
import com.example.water_nn.domain.usecases.ValidateNewOrderDataUseCase
import com.example.water_nn.presentation.main.Contract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class NewOrderViewModel(
    private val createNewOrderUseCase: CreateNewOrderUseCase,
    private val validateNewOrderDataUseCase: ValidateNewOrderDataUseCase
) : ViewModel(), Contract.INewOrderViewModel {

    override val validationStatusList: MutableLiveData<List<ValidationStatus>> by lazy { MutableLiveData() }

    override fun createOrder(orderData: OrderData) {
        validationStatusList.value = emptyList()

        viewModelScope.launch(Dispatchers.IO) {
            if (isOrderDataValid(orderData)) {
                val newOrder = with(orderData) {
                    Order(
                        id = 0,
                        name = name,
                        address = address,
                        phoneNumber = phoneNumber,
                        quantityWater = 2,          //hardcoded
                        quantityEmptyBottle = 2,    //hardcoded
                        waterPrice = 180.0,         //hardcoded
                        emptyBottlePrice = 200.0,   //hardcoded
                        deliveryDay = deliveryDay,
                        deliveryTime = deliveryTime,
                        description = description,
                        totalPrice = 360.0          //hardcoded
                    )
                }
                createNewOrderUseCase.execute(newOrder)
            }
        }
    }

    override suspend fun isOrderDataValid(orderData: OrderData): Boolean {

        val validationList = viewModelScope.async(Dispatchers.IO) {
            return@async validateNewOrderDataUseCase.execute(orderData)
        }.await()

        validationStatusList.postValue(validationList)

        return validationList.contains(ValidationStatus.SUCCESS)
    }
}


