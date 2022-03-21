package com.example.water_nn.presentation.main.profile

import com.example.water_nn.domain.models.UserInformation
import com.example.water_nn.domain.usecases.GetLocalUserInfoUseCase
import com.example.water_nn.domain.usecases.SaveUserInformationUseCase
import com.example.water_nn.presentation.BaseViewModel
import com.example.water_nn.presentation.main.profile.ProfileEvent as Event
import com.example.water_nn.presentation.main.profile.ProfileState as State

class ProfileViewModel(
    private val getLocalUserInfoUseCase: GetLocalUserInfoUseCase,
    private val saveUserInformationUseCase: SaveUserInformationUseCase
) : BaseViewModel<State, Event>(State()) {

    override suspend fun handleEvent(event: Event) {
        when (event) {
            is Event.ProfileInfoRequest -> getProfileInfo()
            is Event.SaveUserInformation -> saveUserInformation(event.userInfo)
            is Event.NameChanged -> newState(Mutation.newUserName(event.name))
            is Event.EmailChanged -> newState(Mutation.newEmail(event.email))
            is Event.PhoneChanged -> newState(Mutation.newPhone(event.phone))
        }
    }

    private suspend fun getProfileInfo() {
        runSeparate {
            newState(Mutation.withLoading(true))
            fireOneShot(ProfileOneShot.SetUserInfo(getLocalUserInfoUseCase()))
            newState(Mutation.withLoading(false))
        }
    }

    private fun saveUserInformation(userInformation: UserInformation) {
        runSeparate {
            newState(Mutation.withLoading(true))
            saveUserInformationUseCase.execute(userInformation)
            newState(Mutation.withLoading(false))
        }
    }
}