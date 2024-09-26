package name.patrikspano.mobilecasestudy.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinData(
    val id: String,
    val name: String,
    val symbol: String,
    @SerialName("current_price")
    val currentPrice: Double,
    @SerialName("market_cap")
    val marketCap: Long,
    @SerialName("price_change_percentage_24h")
    val priceChangePercentage24h: Double,
    @SerialName("sparkline_in_7d")
    val sparkline: SparklineData
)

@Serializable
data class SparklineData(
    val price: List<Double>
)

@Serializable
data class SearchCoin(
    val id: String,
    val name: String,
    @SerialName("api_symbol")
    val apiSymbol: String,
    val symbol: String,
    @SerialName("market_cap_rank")
    val marketCapRank: Long,
    val thumb: String,
    val large: String
)

@Serializable
data class TrendingCoin(
    val id: String,
    @SerialName("coin_id")
    val coinId: Long,
    val name: String,
    val symbol: String,
    @SerialName("market_cap_rank")
    val marketCapRank: Long,
    val thumb: String,
    val small: String,
    val large: String,
    val slug: String,
    @SerialName("price_btc")
    val priceBtc: Double,
    val score: Long
)