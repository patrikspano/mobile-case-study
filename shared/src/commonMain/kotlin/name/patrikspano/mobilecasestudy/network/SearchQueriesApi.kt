package name.patrikspano.mobilecasestudy.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import name.patrikspano.mobilecasestudy.entity.SearchCoin

class SearchQueriesApi {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }
    suspend fun getAllSearchCoins(query: String): List<SearchCoin> {
        return httpClient.get("https://api.coingecko.com/api/v3/search"){
            parameter("query", query)
        }.body()
    }
}