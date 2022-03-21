package com.example.water_nn.presentation.common.events

import kotlinx.coroutines.flow.Flow

interface EventBus<Event> {
    val events: Flow<Event>

    suspend fun sendEvent(event: Event)
    fun trySendEvent(event: Event)
}