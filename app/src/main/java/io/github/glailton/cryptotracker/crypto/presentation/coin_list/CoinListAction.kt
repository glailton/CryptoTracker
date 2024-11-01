package io.github.glailton.cryptotracker.crypto.presentation.coin_list

import io.github.glailton.cryptotracker.crypto.presentation.models.CoinUi

sealed interface CoinListAction {
    data class OnCoinClick(val coinUi: CoinUi): CoinListAction
}