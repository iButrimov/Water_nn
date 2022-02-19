package com.example.water_nn.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.water_nn.data.database.entity.Order
import com.example.water_nn.domain.models.*
import com.example.water_nn.presentation.main.history.ItemOrderCard

interface Contract {

    interface ISplashViewModel {
        val isUserCreated: LiveData<Boolean>
        suspend fun checkUserCreated()
    }

    interface IAuthViewModel {
        val validationAuthStatusList: LiveData<List<AuthValidationStatus>>
        fun createUser(authData: AuthData)
        suspend fun isAuthDataValid(authData: AuthData): Boolean
    }

    interface IAllOrdersViewModel {
        val order: LiveData<Order>
        val orderList: LiveData<List<Order>>
        fun deleteOrder(order: Order)
        fun getOrder(id: String)
        fun getAllOrders()
    }

    interface INewOrderViewModel {
        var name: String
        var address: String
        var phoneNumber: String
        var street: String
        var building: String
        var floor: String
        var apartment: String
        var qtyFullBottle: Int
        var qtyEmptyBottle: Int
        var deliveryDay: DeliveryDay
        var deliveryTimes: MutableList<DeliveryTime>
        var comment: String
        val validationStatusList: LiveData<List<ValidationStatus>>
        fun createOrder(orderData: OrderData)
        fun getEmptyOrder()
        fun getOrderById(id: String)
        suspend fun isOrderDataValid(orderData: OrderData): Boolean

        //        val selectedOrder: MutableLiveData<Order>
        val newOrderItems: MutableLiveData<List<ItemOrderCard>>
        fun nameChanged(changedName: String)
        fun addressChanged(changedAddress: String)
        fun phoneChanged(changedPhone: String)
        fun streetChanged(changedStreet: String)
        fun buildingChanged(changedBuilding: String)
        fun floorChanged(changedFloor: String)
        fun apartmentChanged(changedApartment: String)
        fun qtyFullBottleChange(qty: Int)
        fun qtyEmptyBottleChange(qty: Int)
        fun plusQtyFullBottle()
        fun minusQtyFullBottle()
        fun plusQtyEmptyBottle()
        fun minusQtyEmptyBottle()
        fun deliveryDayClicked(deliveryDay: DeliveryDay)
        fun deliveryTimeClicked(deliveryTime: DeliveryTime)
        fun commentChanged(changedComment: String)
        var priceFullBottle: Double
        var priceEmptyBottle: Double
    }

    interface IProfileViewModel {
        val userInfoLiveData: MutableLiveData<UserInformation>
        var name: String
        var phoneNumber: String
        var address: String
        var buildingNumber: String
        var floorNumber: String
        var apartmentNumber: String
        fun getUserInfo()

        fun saveUserInformation(userInformation: UserInformation)
        val btnIsActive: MutableLiveData<Boolean>
    }
}