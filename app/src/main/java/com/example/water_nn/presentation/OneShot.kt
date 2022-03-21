package com.example.water_nn.presentation

interface OneShot
class OneShotError(
    val exception: Exception,
    val message: String? = exception.message
) : OneShot