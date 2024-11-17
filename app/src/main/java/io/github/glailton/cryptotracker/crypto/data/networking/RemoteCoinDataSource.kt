package io.github.glailton.cryptotracker.crypto.data.networking

import io.github.glailton.cryptotracker.core.data.networking.constructUrl
import io.github.glailton.cryptotracker.core.data.networking.safeCall
import io.github.glailton.cryptotracker.core.domain.util.NetworkError
import io.github.glailton.cryptotracker.core.domain.util.Result
import io.github.glailton.cryptotracker.core.domain.util.map
import io.github.glailton.cryptotracker.crypto.data.mappers.toCoin
import io.github.glailton.cryptotracker.crypto.data.mappers.toCoinPrice
import io.github.glailton.cryptotracker.crypto.data.networking.dto.CoinHistoryDto
import io.github.glailton.cryptotracker.crypto.data.networking.dto.CoinsResponseDto
import io.github.glailton.cryptotracker.crypto.domain.Coin
import io.github.glailton.cryptotracker.crypto.domain.CoinDataSource
import io.github.glailton.cryptotracker.crypto.domain.CoinPrice
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.ZoneId
import java.time.ZonedDateTime

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

    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError> {
        val startMillis = start
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()
        val endMillis = end
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()
        return safeCall<CoinHistoryDto> {
            httpClient.get(
                urlString = constructUrl("/assets/$coinId/history")
            ) {
                parameter("interval", "h6")
                parameter("start", startMillis)
                parameter("end", endMillis)
            }
        }.map { response ->
            response.data.map { it.toCoinPrice() }
        }
    }
}