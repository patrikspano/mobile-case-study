package name.patrikspano.mobilecasestudy

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import name.patrikspano.mobilecasestudy.entity.CoinData
import name.patrikspano.mobilecasestudy.entity.SearchCoin
import name.patrikspano.mobilecasestudy.entity.TrendingCoin
import name.patrikspano.mobilecasestudy.cache.IOSDatabaseDriverFactory
import name.patrikspano.mobilecasestudy.network.CoinsListApi
import name.patrikspano.mobilecasestudy.network.SearchQueriesApi
import name.patrikspano.mobilecasestudy.network.TrendingSearchListApi
import org.koin.core.context.startKoin
import org.koin.dsl.module

class KoinHelper : KoinComponent {
    private val sdk: CryptoSDK by inject()

    // Fetch trending coins
    suspend fun getTrendingCoins(forceReload: Boolean): List<TrendingCoin> {
        return sdk.getTrendingCoins(forceReload)
    }

    // Fetch coin data, specifically for favorites
    suspend fun getCoinData(forceReload: Boolean): List<CoinData> {
        return sdk.getCoinData(forceReload)
    }

    // Search for coins based on query
    suspend fun getSearchCoins(query: String, forceReload: Boolean): List<SearchCoin> {
        return sdk.searchCoins(query, forceReload)
    }

    // Update the favorite status of a coin
    suspend fun updateFavoriteStatus(coin: CoinData) {
        sdk.updateFavoriteStatus(coin)
    }
}

// Initialize Koin with required modules for dependency injection
fun initKoin() {
    startKoin {
        modules(module {
            single { CoinsListApi() }
            single { SearchQueriesApi() }
            single { TrendingSearchListApi() }
            single {
                CryptoSDK(
                    databaseDriverFactory = IOSDatabaseDriverFactory(),
                    coinsListApi = get(),
                    searchQueriesApi = get(),
                    trendingSearchListApi = get()
                )
            }
        })
    }
}