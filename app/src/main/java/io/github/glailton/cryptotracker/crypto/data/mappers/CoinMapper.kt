package io.github.glailton.cryptotracker.crypto.data.mappers

import io.github.glailton.cryptotracker.crypto.data.networking.dto.CoinDto
import io.github.glailton.cryptotracker.crypto.domain.Coin

fun CoinDto.toCoin(): Coin {
    return Coin(
        id = id,
        rank = rank,
        name = name,
        symbol = symbol,
        marketCapUsd = marketCapUsd,
        priceUsd = priceUsd,
        changePercent24Hr = changePercent24Hr
    )
}