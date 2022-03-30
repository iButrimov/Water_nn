package com.example.water_nn.presentation.main.orders

import com.example.water_nn.domain.usecases.GetAllOrdersUseCase
import com.example.water_nn.presentation.BaseViewModel
import kotlinx.coroutines.flow.collect
import com.example.water_nn.presentation.main.orders.OrdersEvent as Event
import com.example.water_nn.presentation.main.orders.OrdersState as State

class OrdersViewModel(
    private val getAllOrdersUseCase: GetAllOrdersUseCase
) : BaseViewModel<State, Event>(State()) {

    override suspend fun handleEvent(event: Event) {
        when (event) {
            is Event.OrderItemClicked -> handleOrderItemClicked(event.id)
            is Event.OrdersListRequest -> getOrders()
        }
    }

    private fun handleOrderItemClicked(id: String) {
        fireOneShot(OrderOneShot.ShowOrderOneShot(id))
    }

    private fun getOrders() {
        runSeparate {
            getAllOrdersUseCase().collect {
                newState(State(it))
            }
        }
    }
}