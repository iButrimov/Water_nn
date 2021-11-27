package com.example.water_nn.domain.usecases

import com.example.water_nn.domain.models.AuthData
import com.example.water_nn.domain.models.AuthValidationStatus
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ValidateAuthDataUseCaseTest {

    private lateinit var validateAuthDataUseCase: ValidateAuthDataUseCase

    @Before
    fun before() {
        validateAuthDataUseCase = ValidateAuthDataUseCase()
    }

    @Test
    fun `validation auth dataList success`() {
        val authData = AuthData("Ivan", "+79200000000")

        val result = validateAuthDataUseCase.execute(authData)

        assertEquals(1, result.size)
        assertEquals(AuthValidationStatus.SUCCESS, result[0])
    }

    @Test
    fun `validation auth dataList failure`() {
        val authData = AuthData("", "")

        val result = validateAuthDataUseCase.execute(authData)

        assertEquals(2, result.size)
        assert(result.contains(AuthValidationStatus.NAME_FIELD_IS_EMPTY))
        assert(result.contains(AuthValidationStatus.PHONE_NUMBER_FIELD_IS_EMPTY))
    }
}