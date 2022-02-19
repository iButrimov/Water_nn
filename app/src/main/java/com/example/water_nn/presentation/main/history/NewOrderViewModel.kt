package com.example.water_nn.presentation.main.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.water_nn.data.database.entity.Order
import com.example.water_nn.domain.models.DeliveryDay
import com.example.water_nn.domain.models.DeliveryTime
import com.example.water_nn.domain.models.OrderData
import com.example.water_nn.domain.models.ValidationStatus
import com.example.water_nn.domain.usecases.*
import com.example.water_nn.presentation.main.Contract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*

class NewOrderViewModel(
    private val createNewOrderUseCase: CreateNewOrderUseCase,
    private val validateNewOrderDataUseCase: ValidateNewOrderDataUseCase,
    private val getOrderByIdUseCase: GetOrderByIdUseCase,
    private val getPriceFullBottleUseCase: GetPriceFullBottleUseCase,
    private val getPriceEmptyBottleUseCase: GetPriceEmptyBottleUseCase
) : ViewModel(), Contract.INewOrderViewModel {

    override val validationStatusList: MutableLiveData<List<ValidationStatus>> by lazy { MutableLiveData() }
    override val newOrderItems = MutableLiveData<List<ItemOrderCard>>()

    override var name: String = ""
    override var address: String = ""
    override var phoneNumber: String = ""
    override var street: String = ""
    override var building: String = ""
    override var floor: String = ""
    override var apartment: String = ""
    override var comment: String = ""

    override var priceFullBottle: Double = 0.0
        set(value) {
            field = value
            buildNewOrderItems()
        }

    override var priceEmptyBottle: Double = 0.0
        set(value) {
            field = value
            buildNewOrderItems()
        }

    override var qtyFullBottle: Int = 1
        set(value) {
            field = value
            recalculatePrices()
            buildNewOrderItems()
        }
    override var qtyEmptyBottle: Int = 1
        set(value) {
            field = value
            recalculatePrices()
            buildNewOrderItems()
        }

    override var deliveryDay: DeliveryDay = DeliveryDay.TODAY
        set(value) {
            field = value
            buildNewOrderItems()
        }

    override var deliveryTimes: MutableList<DeliveryTime> = mutableListOf()
        set(value) {
            field = value
            buildNewOrderItems()
        }

    override fun createOrder(orderData: OrderData) {
        validationStatusList.value = emptyList()

//        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
//        val currentDate = sdf.format(Date())

        viewModelScope.launch(Dispatchers.IO) {
            if (isOrderDataValid(orderData)) {
                val newOrder = with(orderData) {
                    Order(
                        id = 0,
                        name = name,
                        address = address,
                        street = street,
                        building = building,
                        floor = floor,
                        apartment = apartment,
                        phoneNumber = phoneNumber,
                        quantityWater = quantityFullBottle,
                        quantityEmptyBottle = quantityEmptyBottle,
                        waterPrice = priceFullBottle,
                        emptyBottlePrice = priceEmptyBottle,
                        deliveryDay = deliveryDay,
                        deliveryTime = deliveryTime,
                        comment = comment,
                        totalPrice = calculateTotalPrice()
                    )
                }

                createNewOrderUseCase.execute(newOrder)
            }
        }
    }

    override fun getEmptyOrder() {
        buildNewOrderItems()
    }

    override fun getOrderById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val order = viewModelScope.async(Dispatchers.IO) {
                return@async getOrderByIdUseCase.execute(id)
            }.await()
            name = order.name ?: ""
            address = order.address
            phoneNumber = order.phoneNumber
            street = order.street
            building = order.building
            floor = order.floor
            apartment = order.apartment
            comment = order.comment
            qtyFullBottle = order.quantityWater
            qtyEmptyBottle = order.quantityEmptyBottle
            deliveryDay = order.deliveryDay
            deliveryTimes = order.deliveryTime as MutableList<DeliveryTime>
        }
    }

    override fun nameChanged(changedName: String) {
        name = changedName
    }

    override fun addressChanged(changedAddress: String) {
        address = changedAddress
    }

    override fun phoneChanged(changedPhone: String) {
        phoneNumber = changedPhone
    }

    override fun streetChanged(changedStreet: String) {
        street = changedStreet
    }

    override fun buildingChanged(changedBuilding: String) {
        building = changedBuilding
    }

    override fun floorChanged(changedFloor: String) {
        floor = changedFloor
    }

    override fun apartmentChanged(changedApartment: String) {
        apartment = changedApartment
    }

    override fun qtyFullBottleChange(qty: Int) {
        qtyFullBottle = qty
    }

    override fun qtyEmptyBottleChange(qty: Int) {
        qtyEmptyBottle = qty
    }

    override fun plusQtyFullBottle() {
        if (qtyFullBottle < 100) {
            qtyFullBottle++
        }
    }

    override fun minusQtyFullBottle() {
        if (qtyFullBottle > 1) {
            qtyFullBottle--
            if (qtyFullBottle < qtyEmptyBottle) {
                qtyEmptyBottle--
            }
        }
    }

    override fun plusQtyEmptyBottle() {
        if (qtyEmptyBottle < 100 && qtyEmptyBottle < qtyFullBottle) {
            qtyEmptyBottle++
        }
    }

    override fun minusQtyEmptyBottle() {
        if (qtyEmptyBottle > 0) {
            qtyEmptyBottle--
        }
    }

    override fun deliveryDayClicked(deliveryDay: DeliveryDay) {
        this.deliveryDay = deliveryDay
    }

    override fun deliveryTimeClicked(deliveryTime: DeliveryTime) {
        if (deliveryTimes.contains(deliveryTime)) {
            deliveryTimes.remove(deliveryTime)
        } else {
            deliveryTimes.add(deliveryTime)
        }
    }

    override fun commentChanged(changedComment: String) {
        comment = changedComment
    }

    private fun recalculatePrices() {
        val fullBottlePrice = getPriceFullBottleUseCase.execute()
        val emptyBottlePrice = getPriceEmptyBottleUseCase.execute()

        priceFullBottle = qtyFullBottle * fullBottlePrice
        priceEmptyBottle = (qtyFullBottle - qtyEmptyBottle) * emptyBottlePrice
    }

    private fun calculateTotalPrice(): Double {
        return priceFullBottle + priceEmptyBottle
    }

    override suspend fun isOrderDataValid(orderData: OrderData): Boolean {

        val validationList = viewModelScope.async(Dispatchers.IO) {
            return@async validateNewOrderDataUseCase.execute(orderData)
        }.await()

        validationStatusList.postValue(validationList)

        return validationList.contains(ValidationStatus.SUCCESS)
    }

    private fun buildNewOrderItems() {
        NewOrderUtils.buildNewOrderItems(
            name,
            address,
            phoneNumber,
            street,
            building,
            floor,
            apartment,
            priceFullBottle,
            priceEmptyBottle,
            qtyFullBottle,
            qtyEmptyBottle,
            deliveryDay,
            deliveryTimes,
            comment
        ).also {
            newOrderItems.postValue(it)
        }
    }
}


