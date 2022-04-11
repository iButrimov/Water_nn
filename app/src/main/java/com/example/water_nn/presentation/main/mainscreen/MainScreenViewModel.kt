package com.example.water_nn.presentation.main.mainscreen

import com.example.water_nn.presentation.BaseViewModel
import com.example.water_nn.presentation.main.mainscreen.MainScreenEvent as Event
import com.example.water_nn.presentation.main.mainscreen.MainScreenState as State

class MainScreenViewModel() : BaseViewModel<State, Event>(State()) {

    override suspend fun handleEvent(event: Event) {

    }
}