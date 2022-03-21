package com.example.water_nn.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.water_nn.domain.common.exception.DispatcherType
import com.example.water_nn.presentation.common.events.EventBus
import com.example.water_nn.presentation.common.events.SingleShotEventBus
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.koin.core.qualifier.qualifier
import org.koin.java.KoinJavaComponent.inject
import timber.log.Timber

typealias Mutator<TState> = (TState) -> TState

private const val extraBufferCapacity = 5

abstract class BaseViewModel<TState, TEvent>(initialState: TState) : ViewModel() {
    private val _state: MutableStateFlow<TState> = MutableStateFlow(initialState)
    val state: StateFlow<TState> = _state.asStateFlow()

    private val eventBus: EventBus<TEvent> = SingleShotEventBus(extraBufferCapacity)

    private val oneShotBus: EventBus<OneShot> = SingleShotEventBus(extraBufferCapacity)
    val oneShotFlow: Flow<OneShot> = oneShotBus.events

    protected val viewModelExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e("Unhandled exception: $throwable")
    }

    private val dispatcher: CoroutineDispatcher by inject(
        CoroutineDispatcher::class.java,
        DispatcherType.BACKGROUND.qualifier
    )

    val currentState
        get() = state.value

    init {
        viewModelScope.launch(viewModelExceptionHandler + dispatcher) {
            eventBus.events.collect {
                handleEvent(it)
            }
        }
    }

    abstract suspend fun handleEvent(event: TEvent)

    protected fun newState(newState: TState) {
        _state.value = newState
    }

    protected fun newState(vararg mutators: Mutator<TState>) {
        val newState =
            mutators.fold(currentState) { acc: TState, mutator: Mutator<TState> -> mutator(acc) }
        newState(newState)
    }

    protected fun fireOneShot(oneShot: OneShot) {
        oneShotBus.trySendEvent(oneShot)
    }

    protected fun fireOneShotError(e: Exception) {
        oneShotBus.trySendEvent(OneShotError(e))
    }

    fun sendEvent(event: TEvent) {
        eventBus.trySendEvent(event).also {
            Timber.d("Send event: $event: $it")
        }
    }

    protected fun runSeparate(block: suspend () -> Unit): Job {
        return viewModelScope.launch(viewModelExceptionHandler + dispatcher) {
            block()
        }
    }

    protected suspend fun doTry(action: suspend () -> Unit) {
        doTry(action, null)
    }

    protected suspend fun doTry(action: suspend () -> Unit, onErrorAction: (() -> Unit)?) {
        try {
            action()
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Timber.w("catch exception in viewModel $e")
            onErrorAction?.invoke()
            fireOneShotError(e)
        }
    }
}