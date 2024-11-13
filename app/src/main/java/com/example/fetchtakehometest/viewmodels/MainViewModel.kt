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
import androidx.lifecycle.viewModelScope
import com.example.fetchtakehometest.datamodel.FetchDataElement
import com.example.fetchtakehometest.datamodel.ResultWrapper
import com.example.fetchtakehometest.services.FetchDataRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val application: Application,
    private val fetchDataRepository: FetchDataRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<MainViewModelState> = MutableStateFlow(MainViewModelState())
    val uiState: StateFlow<MainViewModelState> = _uiState.asStateFlow()

    private val _exceptions = Channel<Throwable>(Channel.BUFFERED)
    val exceptions = _exceptions.receiveAsFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            _exceptions.send(throwable)
        }
    }

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
                isLoading = true
            )
        }
        viewModelScope.launch(coroutineExceptionHandler) {
            val fetchResult = fetchDataRepository.getFetchData()
            updateUiState { old ->
                old.copy(
                    isLoading = false
                )
            }

            when (fetchResult) {
                is ResultWrapper.GenericError -> {
                    _exceptions.send(fetchResult.exception ?: Exception("Generic, Unknown Error"))
                }

                is ResultWrapper.HttpStatusError -> {
                    _exceptions.send(fetchResult.exception ?: Exception("Generic, Http Error"))
                }

                is ResultWrapper.Success -> {
                    updateUiState { old ->
                        old.copy(
                            fetchDataElements = fetchResult.data
                                ?.filter { it.name?.isNotEmpty() ?: false }
                                ?.sortedWith(
                                    compareBy({ it.listId }, { it.name })
                                )
                        )
                    }
                }
            }
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