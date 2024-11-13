package com.example.fetchtakehometest.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.flowWithLifecycle
import com.example.fetchtakehometest.datamodel.FetchDataElement
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.receiveAsFlow

class MainViewModel(
    private val application: Application
) : ViewModel() {

    private val _uiState: MutableStateFlow<MainViewModelState> = MutableStateFlow(MainViewModelState())
    val uiState: StateFlow<MainViewModelState> = _uiState.asStateFlow()

    private val _exceptions = Channel<Exception>(Channel.BUFFERED)
    val exceptions = _exceptions.receiveAsFlow()

    private fun updateUiState(updateAction: (MainViewModelState) -> MainViewModelState) {
        _uiState.update {
            updateAction.invoke(it)
        }
    }

    @Composable
    fun collectAsStateWithLifecycle(): State<MainViewModelState> {
        val lifecycleOwner = LocalLifecycleOwner.current

        val stateFlowLifecycleAware = remember(key1 = uiState, key2 = lifecycleOwner) {
            uiState.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
        }

        return stateFlowLifecycleAware.collectAsState(initial = MainViewModelState())
    }

    fun onFetch() {
        updateUiState { old ->
            old.copy(
                fetchDataElements = listOf(
                    FetchDataElement(
                        id = 1,
                        listId = 1,
                        name = "One"
                    ),
                    FetchDataElement(
                        id = 2,
                        listId = 2,
                        name = "Two"
                    ),
                    FetchDataElement(
                        id = 3,
                        listId = 3,
                        name = "Three"
                    ),
                    FetchDataElement(
                        id = 4,
                        listId = 4,
                        name = "Four"
                    ),
                    FetchDataElement(
                        id = 5,
                        listId = 5,
                        name = "Five"
                    ),
                )
            )
        }
    }

    fun onReset() {
        updateUiState { old ->
            old.copy(
                fetchDataElements = null
            )
        }
    }
}