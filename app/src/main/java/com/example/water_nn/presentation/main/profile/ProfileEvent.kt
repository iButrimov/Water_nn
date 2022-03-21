package com.example.water_nn.presentation.main.profile

import com.example.water_nn.domain.models.UserInformation
import com.example.water_nn.presentation.OneShot

sealed class ProfileEvent {
    object ProfileInfoRequest : ProfileEvent()
    class SaveUserInformation(val userInfo: UserInformation) : ProfileEvent()
    class NameChanged(val name: String) : ProfileEvent()
    class EmailChanged(val email: String) : ProfileEvent()
    class PhoneChanged(val phone: String) : ProfileEvent()
}

sealed class ProfileOneShot : OneShot {
    class SetUserInfo(val userInformation: UserInformation) : ProfileOneShot()
    object Logout : ProfileOneShot()
}