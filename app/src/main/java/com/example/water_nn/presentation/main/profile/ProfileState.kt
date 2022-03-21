package com.example.water_nn.presentation.main.profile

import com.example.water_nn.presentation.Mutator

data class ProfileState(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val isLoading: Boolean = false
) {
    private val isNameValid = name.isNotBlank()
    private val isEmailValid = email.isNotBlank()
    private val isPhoneValid = phone.isNotBlank()

    val isDataValid: Boolean = isNameValid && isEmailValid && isPhoneValid
}

object Mutation {

    fun newUserName(newUserName: String): Mutator<ProfileState> = {
        it.copy(
            name = newUserName,
        )
    }

    fun newEmail(newEmail: String): Mutator<ProfileState> = {
        it.copy(
            email = newEmail,
        )
    }

    fun newPhone(newPhone: String): Mutator<ProfileState> = {
        it.copy(
            phone = newPhone,
        )
    }

    fun withLoading(isLoading: Boolean): Mutator<ProfileState> = {
        it.copy(
            isLoading = isLoading
        )
    }
}