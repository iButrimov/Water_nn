package com.example.water_nn.domain.usecases

import com.example.water_nn.domain.models.DeliveryDay
import com.example.water_nn.domain.models.DeliveryTime
import com.example.water_nn.domain.models.OrderData
import com.example.water_nn.domain.models.ValidationStatus
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ValidateNewOrderDataUseCaseTest {

    private lateinit var validateNewOrderDataUseCase: ValidateNewOrderDataUseCase

    @Before
    fun init() {
        validateNewOrderDataUseCase = ValidateNewOrderDataUseCase()
    }

    @Test
    fun `validate new order success`() {
        val dataOrder = OrderData(
            name = "Ivan",
            address = "Lenina",
            phoneNumber = "123456789",
            quantityEmptyBottle = 2,
            quantityFullBottle = 2,
            deliveryDay = DeliveryDay.TOMORROW,
            deliveryTime = listOf(DeliveryTime.AFTERNOON),
            comment = ""
        )

        val result = validateNewOrderDataUseCase.execute(dataOrder)

        assertEquals(1, result.size)
        assertEquals(ValidationStatus.SUCCESS, result[0])
    }

    @Test
    fun `validate new order failure`() {
        val dataOrder = OrderData(
            name = "Ivan",
            address = "",
            phoneNumber = "",
            quantityEmptyBottle = 2,
            quantityFullBottle = 2,
            deliveryDay = DeliveryDay.TOMORROW,
            deliveryTime = listOf(DeliveryTime.AFTERNOON),
            comment = ""
        )

        val result = validateNewOrderDataUseCase.execute(dataOrder)

        assertEquals(2, result.size)
        assert(result.contains(ValidationStatus.ADDRESS_FIELD_IS_EMPTY))
        assert(result.contains(ValidationStatus.PHONE_NUMBER_FIELD_IS_EMPTY))
    }
}