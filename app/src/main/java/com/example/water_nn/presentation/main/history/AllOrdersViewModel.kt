package com.example.water_nn.presentation.main.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.water_nn.data.database.entity.Order
import com.example.water_nn.domain.usecases.DeleteOrderUseCase
import com.example.water_nn.domain.usecases.GetAllOrdersUseCase
import com.example.water_nn.domain.usecases.GetOrderUseCase
import com.example.water_nn.presentation.main.Contract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AllOrdersViewModel(
    private val deleteOrderUseCase: DeleteOrderUseCase,
    private val getOrderUseCase: GetOrderUseCase,
    private val getAllOrdersUseCase: GetAllOrdersUseCase
) : ViewModel(), Contract.IAllOrdersViewModel {

    override val order: MutableLiveData<Order> by lazy { MutableLiveData() }
    override val orderList: MutableLiveData<List<Order>> by lazy { MutableLiveData() }

    override fun deleteOrder(order: Order) {
        viewModelScope.launch(Dispatchers.IO)  {
            deleteOrderUseCase.execute(order)
        }
    }

    override fun getOrder(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getOrderUseCase.execute(id).let {
                order.postValue(it)
            }
        }
    }

    override fun getAllOrders() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllOrdersUseCase.execute().collect {
                orderList.postValue(it)
            }
        }
    }
}