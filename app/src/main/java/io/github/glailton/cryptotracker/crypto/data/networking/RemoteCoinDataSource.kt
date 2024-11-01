package io.github.glailton.cryptotracker.crypto.data.networking

import io.github.glailton.cryptotracker.core.data.networking.constructUrl
import io.github.glailton.cryptotracker.core.data.networking.safeCall
import io.github.glailton.cryptotracker.core.domain.util.NetworkError
import io.github.glailton.cryptotracker.core.domain.util.Result
import io.github.glailton.cryptotracker.core.domain.util.map
import io.github.glailton.cryptotracker.crypto.data.mappers.toCoin
import io.github.glailton.cryptotracker.crypto.data.networking.dto.CoinsResponseDto
import io.github.glailton.cryptotracker.crypto.domain.Coin
import io.github.glailton.cryptotracker.crypto.domain.CoinDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RemoteCoinDataSource(
    private val httpClient: HttpClient
): CoinDataSource {
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinsResponseDto> {
            httpClient.get(
                urlString = constructUrl("/assets")
            )
        }.map { response ->
            response.data.map { it.toCoin() }
        }
    }
}