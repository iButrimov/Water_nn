package com.example.water_nn.domain.models

data class UserInformation(
    val name: String,
    val phoneNumber: String,
    val address: String = "",
    val buildingNumber: String = "",
    val floorNumber: String = "",
    val apartmentNumber: String = "",
)