package com.example.water_nn.presentation.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.water_nn.domain.usecases.CheckUserCreatedUseCase
import com.example.water_nn.presentation.main.Contract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class SplashViewModel(private val checkUserCreatedUseCase: CheckUserCreatedUseCase) : ViewModel(),
    Contract.ISplashViewModel {

    override val isUserCreated: MutableLiveData<Boolean> by lazy { MutableLiveData() }

    override suspend fun checkUserCreated() {
        val isUserCreatedResult = viewModelScope.async(Dispatchers.IO) {
            checkUserCreatedUseCase.execute()
        }.await()

        isUserCreated.postValue(isUserCreatedResult)
    }
}