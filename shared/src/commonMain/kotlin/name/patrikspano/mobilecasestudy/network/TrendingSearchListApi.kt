package name.patrikspano.mobilecasestudy.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import name.patrikspano.mobilecasestudy.entity.TrendingCoin
import name.patrikspano.mobilecasestudy.entity.TrendingResponse

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
        return try {
            val response: TrendingResponse = httpClient.get("https://api.coingecko.com/api/v3/search/trending").body()
            response.coins.map { it.item }
        } catch (e: Exception) {
            println("Error fetching trending coins: ${e.message}")
            emptyList() // Return an empty list on failure
        }
    }
}