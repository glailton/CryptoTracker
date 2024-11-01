package io.github.glailton.cryptotracker

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.glailton.cryptotracker.core.presentation.util.ObserveAsEvents
import io.github.glailton.cryptotracker.core.presentation.util.toString
import io.github.glailton.cryptotracker.crypto.presentation.coin_list.CoinListEvent
import io.github.glailton.cryptotracker.crypto.presentation.coin_list.CoinListScreen
import io.github.glailton.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import io.github.glailton.cryptotracker.ui.theme.CryptoTrackerTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel = koinViewModel<CoinListViewModel>()
                    val state by viewModel.state.collectAsStateWithLifecycle()
                    ObserveAsEvents(events = viewModel.events) { event ->
                        when (event) {
                            is CoinListEvent.Error -> {
                                Toast.makeText(
                                    this,
                                    event.error.toString(this),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                    CoinListScreen(
                        state = state,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}