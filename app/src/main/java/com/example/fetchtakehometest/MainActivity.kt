package com.example.fetchtakehometest

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.fetchtakehometest.ui.theme.FetchTakehomeTestTheme
import com.example.fetchtakehometest.viewmodels.MainViewModel
import com.example.fetchtakehometest.viewmodels.MainViewModelState
import com.example.fetchtakehometest.views.MainView
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.coroutines.flow.catch

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FetchTakehomeTestTheme {
                val uiState = mainViewModel.collectAsStateWithLifecycle()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainView(
                        uiState = uiState.value,
                        onFetch = mainViewModel::onFetch,
                        onReset = mainViewModel::onReset,
                        modifier = Modifier
                            .background(Color.LightGray)
                            .padding(innerPadding)
                    )
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.exceptions.catch { e ->
                    // Exception in the flow itself?
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT)
                }.collect { exception ->
                    Toast.makeText(this@MainActivity, exception.message, Toast.LENGTH_SHORT)
                }
            }
        }
    }
}