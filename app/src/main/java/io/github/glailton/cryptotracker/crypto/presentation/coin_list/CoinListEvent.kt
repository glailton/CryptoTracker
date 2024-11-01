package io.github.glailton.cryptotracker.crypto.presentation.coin_list

import io.github.glailton.cryptotracker.core.domain.util.NetworkError

sealed interface CoinListEvent {
    data class Error(val error: NetworkError): CoinListEvent
}