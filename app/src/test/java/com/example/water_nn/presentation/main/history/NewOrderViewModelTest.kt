package com.example.water_nn.presentation.main.history

import com.example.water_nn.data.database.entity.Order
import com.example.water_nn.domain.models.DeliveryDay
import com.example.water_nn.domain.models.DeliveryTime
import com.example.water_nn.domain.models.OrderData
import com.example.water_nn.domain.usecases.*
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class NewOrderViewModelTest {

    private val createNewOrderUseCase = mockk<CreateNewOrderUseCase>()
    private val validateNewOrderDataUseCase = mockk<ValidateNewOrderDataUseCase>()
    private val getOrderByIdUseCase = mockk<GetOrderByIdUseCase>()
    private val getPriceFullBottleUseCase = mockk<GetPriceFullBottleUseCase>()
    private val getPriceEmptyBottleUseCase = mockk<GetPriceEmptyBottleUseCase>()

    lateinit var newOrderViewModel: NewOrderViewModel

    @Before
    fun before() {
        newOrderViewModel = NewOrderViewModel(
            createNewOrderUseCase,
            validateNewOrderDataUseCase,
            getOrderByIdUseCase,
            getPriceFullBottleUseCase,
            getPriceEmptyBottleUseCase,
        )
    }

    @Test
    fun `create new order success`() {
        val orderData = OrderData(
            name = "Ivan",
            address = "qwe",
            phoneNumber = "123",
            quantityFullBottle = 1,
            quantityEmptyBottle = 1,
            deliveryDay = DeliveryDay.TOMORROW,
            deliveryTime = listOf(DeliveryTime.EVENING,DeliveryTime.AFTERNOON),
            comment = "456"
        )

        val order = with(orderData) {
            Order(
                id = 0,
                name = name,
                address = address,
                phoneNumber = phoneNumber,
                quantityWater = quantityFullBottle,
                quantityEmptyBottle = quantityEmptyBottle,
                waterPrice = 0.0,
                emptyBottlePrice = 0.0,
                deliveryDay = deliveryDay,
                deliveryTime = deliveryTime,
                comment = comment,
                totalPrice = 0.0
            )
        }

        newOrderViewModel.createOrder(orderData)

        coVerify {
            createNewOrderUseCase.execute(order)
        }
    }

}
