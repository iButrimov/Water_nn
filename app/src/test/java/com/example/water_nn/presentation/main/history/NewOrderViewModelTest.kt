package com.example.water_nn.presentation.main.history

import androidx.lifecycle.MutableLiveData
import com.example.water_nn.data.database.entity.Order
import com.example.water_nn.domain.models.DeliveryDay
import com.example.water_nn.domain.models.DeliveryTime
import com.example.water_nn.domain.models.OrderData
import com.example.water_nn.domain.models.ValidationStatus
import com.example.water_nn.domain.usecases.*
import com.example.water_nn.presentation.BaseViewModelTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class NewOrderViewModelTest : BaseViewModelTest() {

    private val createNewOrderUseCase = mockk<CreateNewOrderUseCase>()
    private val validateNewOrderDataUseCase = mockk<ValidateNewOrderDataUseCase>()
    private val getOrderByIdUseCase = mockk<GetOrderByIdUseCase>()
    private val getPriceFullBottleUseCase = mockk<GetPriceFullBottleUseCase>()
    private val getPriceEmptyBottleUseCase = mockk<GetPriceEmptyBottleUseCase>()

    lateinit var newOrderViewModel: NewOrderViewModel
    private lateinit var validationStatusList: MutableLiveData<List<ValidationStatus>>

    @Before
    fun before() {
        newOrderViewModel = NewOrderViewModel(
            createNewOrderUseCase,
            validateNewOrderDataUseCase,
            getOrderByIdUseCase,
            getPriceFullBottleUseCase,
            getPriceEmptyBottleUseCase,
        )
        validationStatusList = mockk()
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
            deliveryTime = listOf(DeliveryTime.EVENING, DeliveryTime.AFTERNOON),
            comment = "456"
        )

        coEvery { validateNewOrderDataUseCase.execute(orderData) } returns listOf(ValidationStatus.SUCCESS)

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

        coVerify { validateNewOrderDataUseCase.execute(orderData) }

        coVerify { createNewOrderUseCase.execute(order) }
    }

    @Test
    fun `get order by id success`() {
        val id = "1"
        newOrderViewModel.getOrderById(id)
        coVerify { getOrderByIdUseCase.execute(id) }

    }

    @Test
    fun `is order data valid`() = runBlocking {
        val orderData = mockk<OrderData>()
        val validationList = mockk<List<ValidationStatus>>()

        coEvery { validateNewOrderDataUseCase.execute(orderData) } returns validationList
        coEvery { validationList.contains(ValidationStatus.SUCCESS) } returns true
        coEvery { validationStatusList.postValue(validationList) } returns Unit

        newOrderViewModel.isOrderDataValid(orderData)

        coVerify { validateNewOrderDataUseCase.execute(orderData) }
//        coVerify { validationStatusList.postValue(validationList) }
    }
}
