package name.patrikspano.mobilecasestudy.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import name.patrikspano.mobilecasestudy.entity.TrendingCoin

class TrendingSearchListApi {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }
    suspend fun getAllTrendingCoins(): List<TrendingCoin> {
        return httpClient.get("https://api.coingecko.com/api/v3/search/trending").body()
    }
}