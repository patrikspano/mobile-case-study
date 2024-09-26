package name.patrikspano.mobilecasestudy.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import name.patrikspano.mobilecasestudy.entity.CoinData

class CoinsListApi {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }
    suspend fun getAllCoinData(vsCurrency: String = "usd"): List<CoinData> {
        return httpClient.get("https://api.coingecko.com/api/v3/coins/markets") {
            parameter("vs_currency", vsCurrency)
            parameter("order", "market_cap_desc")
            parameter("per_page", 25)
            parameter("sparkline", true)
        }.body()
    }
}