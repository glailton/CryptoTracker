package io.github.glailton.cryptotracker.crypto.domain

import io.github.glailton.cryptotracker.core.domain.util.NetworkError
import io.github.glailton.cryptotracker.core.domain.util.Result

interface CoinDataSource {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
}