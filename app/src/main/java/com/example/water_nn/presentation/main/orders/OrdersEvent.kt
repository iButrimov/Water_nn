package com.example.water_nn.presentation.main.orders

import com.example.water_nn.presentation.OneShot

sealed class OrdersEvent {
    object OrdersListRequest : OrdersEvent()
    class OrderItemClicked(val id: String) : OrdersEvent()
}

sealed class OrderOneShot : OneShot {
    class ShowOrderOneShot(val id: String) : OrderOneShot()
}