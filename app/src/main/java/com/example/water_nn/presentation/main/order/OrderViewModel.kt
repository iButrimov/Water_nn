package com.example.water_nn.presentation.main.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.water_nn.data.database.entity.Order
import com.example.water_nn.domain.usecases.AddNewOrderUseCase
import com.example.water_nn.domain.usecases.DeleteOrderUseCase
import com.example.water_nn.domain.usecases.GetAllOrdersUseCase
import com.example.water_nn.domain.usecases.GetOrderUseCase
import com.example.water_nn.presentation.main.Contract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class OrderViewModel(
    private val addNewOrderUseCase: AddNewOrderUseCase,
    private val deleteOrderUseCase: DeleteOrderUseCase,
    private val getOrderUseCase: GetOrderUseCase,
    private val getAllOrdersUseCase: GetAllOrdersUseCase
) : ViewModel(), Contract.IOrderViewModel {

    override val orderList: MutableLiveData<List<Order>> by lazy { MutableLiveData() }

    override suspend fun addOrder(order: Order) {
        viewModelScope.launch {
            addNewOrderUseCase.execute(order)
        }
    }

    override suspend fun deleteOrder(order: Order) {
        viewModelScope.launch {
            deleteOrderUseCase.execute(order)
        }
    }

    override suspend fun getOrder(id: String): Order {
        return getOrderUseCase.execute(id)
    }

    override fun getAllOrders() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllOrdersUseCase.execute().collect {
                orderList.postValue(it)
            }
        }
    }
}