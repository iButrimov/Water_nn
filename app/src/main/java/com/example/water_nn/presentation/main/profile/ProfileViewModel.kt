package com.example.water_nn.presentation.main.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.water_nn.domain.models.UserInformation
import com.example.water_nn.domain.usecases.GetUserInfoUseCase
import com.example.water_nn.domain.usecases.SaveUserInformationUseCase
import com.example.water_nn.presentation.main.Contract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val saveUserInformationUseCase: SaveUserInformationUseCase
) : ViewModel(), Contract.IProfileViewModel {

    override val userInfoLiveData: MutableLiveData<UserInformation> by lazy { MutableLiveData() }
    override val btnIsActive: MutableLiveData<Boolean> by lazy { MutableLiveData() }

    private var userInfo: UserInformation? = null
        set(value) {
            field = value
            userInfoLiveData.postValue(value)
        }

    override var name: String = ""
        set(value) {
            field = value
            compareUserInformation()
        }
    override var phoneNumber: String = ""
        set(value) {
            field = value
            compareUserInformation()
        }
    override var address: String = ""
        set(value) {
            field = value
            compareUserInformation()
        }
    override var buildingNumber: String = ""
        set(value) {
            field = value
            compareUserInformation()
        }
    override var floorNumber: String = ""
        set(value) {
            field = value
            compareUserInformation()
        }
    override var apartmentNumber: String = ""
        set(value) {
            field = value
            compareUserInformation()
        }

    private fun compareUserInformation() {
        val currentUserInfo = UserInformation(
            name,
            phoneNumber,
            address,
            buildingNumber,
            floorNumber,
            apartmentNumber
        )

        btnIsActive.postValue(currentUserInfo != userInfo)
    }

    override fun getUserInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val userInformationFromSharedPref = viewModelScope.async(Dispatchers.IO) {
                getUserInfoUseCase.execute()
            }.await()

            userInfo = userInformationFromSharedPref

            setUserInfoToScreen(userInformationFromSharedPref)
        }
    }

    override fun saveUserInformation(userInformation: UserInformation) {
        viewModelScope.launch {
            saveUserInformationUseCase.execute(userInformation)
        }
        btnIsActive.postValue(false)
        getUserInfo()
    }

    private fun setUserInfoToScreen(userInformationFromSharedPref: UserInformation) {
        userInformationFromSharedPref.apply {
            this@ProfileViewModel.name = name
            this@ProfileViewModel.phoneNumber = phoneNumber
            this@ProfileViewModel.address = address
            this@ProfileViewModel.buildingNumber = buildingNumber
            this@ProfileViewModel.floorNumber = floorNumber
            this@ProfileViewModel.apartmentNumber = apartmentNumber
        }
    }
}