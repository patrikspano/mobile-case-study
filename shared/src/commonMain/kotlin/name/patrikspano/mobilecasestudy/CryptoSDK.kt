package name.patrikspano.mobilecasestudy

import name.patrikspano.mobilecasestudy.cache.Database
import name.patrikspano.mobilecasestudy.cache.DatabaseDriverFactory
import name.patrikspano.mobilecasestudy.network.CoinsListApi
import name.patrikspano.mobilecasestudy.network.SearchQueriesApi
import name.patrikspano.mobilecasestudy.network.TrendingSearchListApi
import name.patrikspano.mobilecasestudy.entity.CoinData
import name.patrikspano.mobilecasestudy.entity.SearchCoin
import name.patrikspano.mobilecasestudy.entity.TrendingCoin

class CryptoSDK(databaseDriverFactory: DatabaseDriverFactory,
        private val coinsListApi: CoinsListApi,
        private val searchQueriesApi: SearchQueriesApi,
        private val trendingSearchListApi: TrendingSearchListApi
    ) {
        private val database = Database(databaseDriverFactory)

        @Throws(Exception::class)
        suspend fun getTrendingCoins(forceReload: Boolean): List<TrendingCoin> {
            val cachedTrendingCoins = database.getAllTrendingCoins()
            return if (cachedTrendingCoins.isNotEmpty() && !forceReload) {
                cachedTrendingCoins
            } else {
                trendingSearchListApi.getAllTrendingCoins().also {
                    database.clearAndCreateTrendingCoins(it)
                }
            }
        }

        @Throws(Exception::class)
        suspend fun getCoinData(forceReload: Boolean): List<CoinData> {
            val cachedCoinData = database.getAllCoinData()
            return if (cachedCoinData.isNotEmpty() && !forceReload) {
                cachedCoinData
            } else {
                coinsListApi.getAllCoinData().also {
                    database.clearAndCreateCoinData(it)
                }
            }
        }

        @Throws(Exception::class)
        suspend fun searchCoins(query: String, forceReload: Boolean): List<SearchCoin> {
            val cachedSearchCoins = database.getAllSearchCoins()
            return if (cachedSearchCoins.isNotEmpty() && !forceReload) {
                cachedSearchCoins
            } else {
                searchQueriesApi.getAllSearchCoins(query).also {
                    database.clearAndCreateSearchCoins(it)
                }
            }
        }
    }