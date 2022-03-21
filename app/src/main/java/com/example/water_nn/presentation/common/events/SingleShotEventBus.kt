package com.example.water_nn.presentation.common.events

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

private const val defaultEventChannelCapacity = 10

class SingleShotEventBus<Event>(bufferCapacity: Int = defaultEventChannelCapacity) :
    EventBus<Event> {
    private val _events = Channel<Event>(capacity = bufferCapacity)
    override val events = _events.receiveAsFlow()

    override fun trySendEvent(event: Event) {
        _events.trySend(event)
    }

    override suspend fun sendEvent(event: Event) {
        _events.send(event)
    }
}