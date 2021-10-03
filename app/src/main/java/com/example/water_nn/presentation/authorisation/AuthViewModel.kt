package com.example.water_nn.presentation.authorisation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.water_nn.domain.models.AuthData
import com.example.water_nn.domain.models.AuthValidationStatus
import com.example.water_nn.domain.usecases.CreateNewUserUseCase
import com.example.water_nn.domain.usecases.ValidateAuthDataUseCase
import com.example.water_nn.presentation.main.Contract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AuthViewModel(
    private val createNewUserUseCase: CreateNewUserUseCase,
    private val validateAuthDataUseCase: ValidateAuthDataUseCase
) : ViewModel(), Contract.IAuthViewModel {

    override val validationAuthStatusList: MutableLiveData<List<AuthValidationStatus>> by lazy { MutableLiveData() }

    override fun createUser(authData: AuthData) {
        validationAuthStatusList.value = emptyList()

        viewModelScope.launch(Dispatchers.IO) {
            if(isAuthDataValid(authData)) {
                createNewUserUseCase.execute(authData)
            }
        }
    }

    override suspend fun isAuthDataValid(authData: AuthData): Boolean {
        val validationList = viewModelScope.async(Dispatchers.IO) {
            return@async validateAuthDataUseCase.execute(authData)
        }.await()

        validationAuthStatusList.postValue(validationList)

        return validationList.contains(AuthValidationStatus.SUCCESS)
    }
}