package io.github.glailton.cryptotracker.crypto.data.mappers

import io.github.glailton.cryptotracker.crypto.data.networking.dto.CoinPriceDto
import io.github.glailton.cryptotracker.crypto.domain.CoinPrice
import java.time.Instant
import java.time.ZoneId

fun CoinPriceDto.toCoinPrice(): CoinPrice {
    return CoinPrice(
        priceUsd = priceUsd,
        dateTime = Instant
            .ofEpochMilli(time)
            .atZone(ZoneId.of("UTC"))
    )
}