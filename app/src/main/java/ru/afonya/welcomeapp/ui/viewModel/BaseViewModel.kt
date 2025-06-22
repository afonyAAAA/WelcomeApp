package ru.afonya.test.ui.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<S, E> : ViewModel() {
    protected abstract val _state: MutableStateFlow<S>

    val state: StateFlow<S>
        get() = _state.asStateFlow()

    abstract fun onEvent(event: E)

    protected fun updateUiState(state: S.() -> S) {
        _state.update {
            state(_state.value)
        }
    }
}