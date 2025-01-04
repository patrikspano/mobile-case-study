package name.patrikspano.mobilecasestudy

import name.patrikspano.mobilecasestudy.cache.AndroidDatabaseDriverFactory
import name.patrikspano.mobilecasestudy.network.CoinsListApi
import name.patrikspano.mobilecasestudy.network.SearchQueriesApi
import name.patrikspano.mobilecasestudy.network.TrendingSearchListApi
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single { CoinsListApi() }
    single { SearchQueriesApi() }
    single { TrendingSearchListApi() }
    single {
        CryptoSDK (
            databaseDriverFactory = AndroidDatabaseDriverFactory(
                androidContext()
            ), coinsListApi = get(), searchQueriesApi = get(), trendingSearchListApi = get()
        )
    }
}