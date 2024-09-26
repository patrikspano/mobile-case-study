package name.patrikspano.mobilecasestudy.cache

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.SqlDriver
import kotlin.Any
import kotlin.Double
import kotlin.Long
import kotlin.String

public class AppDatabaseQueries(
  driver: SqlDriver,
) : TransacterImpl(driver) {
  public fun <T : Any> selectAllCoinData(mapper: (
    id: String,
    name: String,
    symbol: String,
    current_price: Double,
    market_cap: Long,
    price_change_percentage_24h: Double?,
    sparkline: String?,
  ) -> T): Query<T> = Query(1_444_661_378, arrayOf("CoinData"), driver, "AppDatabase.sq",
      "selectAllCoinData",
      "SELECT CoinData.id, CoinData.name, CoinData.symbol, CoinData.current_price, CoinData.market_cap, CoinData.price_change_percentage_24h, CoinData.sparkline FROM CoinData") {
      cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getDouble(3)!!,
      cursor.getLong(4)!!,
      cursor.getDouble(5),
      cursor.getString(6)
    )
  }

  public fun selectAllCoinData(): Query<CoinData> = selectAllCoinData { id, name, symbol,
      current_price, market_cap, price_change_percentage_24h, sparkline ->
    CoinData(
      id,
      name,
      symbol,
      current_price,
      market_cap,
      price_change_percentage_24h,
      sparkline
    )
  }

  public fun <T : Any> selectAllSearchCoins(mapper: (
    id: String,
    name: String,
    api_symbol: String,
    symbol: String,
    market_cap_rank: Long,
    thumb: String,
    large: String,
  ) -> T): Query<T> = Query(-1_121_920_205, arrayOf("SearchCoin"), driver, "AppDatabase.sq",
      "selectAllSearchCoins",
      "SELECT SearchCoin.id, SearchCoin.name, SearchCoin.api_symbol, SearchCoin.symbol, SearchCoin.market_cap_rank, SearchCoin.thumb, SearchCoin.large FROM SearchCoin") {
      cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!,
      cursor.getLong(4)!!,
      cursor.getString(5)!!,
      cursor.getString(6)!!
    )
  }

  public fun selectAllSearchCoins(): Query<SearchCoin> = selectAllSearchCoins { id, name,
      api_symbol, symbol, market_cap_rank, thumb, large ->
    SearchCoin(
      id,
      name,
      api_symbol,
      symbol,
      market_cap_rank,
      thumb,
      large
    )
  }

  public fun <T : Any> selectAllTrendingCoins(mapper: (
    id: String,
    coin_id: Long,
    name: String,
    symbol: String,
    market_cap_rank: Long?,
    thumb: String?,
    small: String?,
    large: String?,
    slug: String?,
    price_btc: Double?,
    score: Long,
  ) -> T): Query<T> = Query(-1_671_432_298, arrayOf("TrendingCoin"), driver, "AppDatabase.sq",
      "selectAllTrendingCoins",
      "SELECT TrendingCoin.id, TrendingCoin.coin_id, TrendingCoin.name, TrendingCoin.symbol, TrendingCoin.market_cap_rank, TrendingCoin.thumb, TrendingCoin.small, TrendingCoin.large, TrendingCoin.slug, TrendingCoin.price_btc, TrendingCoin.score FROM TrendingCoin") {
      cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getLong(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!,
      cursor.getLong(4),
      cursor.getString(5),
      cursor.getString(6),
      cursor.getString(7),
      cursor.getString(8),
      cursor.getDouble(9),
      cursor.getLong(10)!!
    )
  }

  public fun selectAllTrendingCoins(): Query<TrendingCoin> = selectAllTrendingCoins { id, coin_id,
      name, symbol, market_cap_rank, thumb, small, large, slug, price_btc, score ->
    TrendingCoin(
      id,
      coin_id,
      name,
      symbol,
      market_cap_rank,
      thumb,
      small,
      large,
      slug,
      price_btc,
      score
    )
  }

  public fun insertCoinData(
    id: String,
    name: String,
    symbol: String,
    current_price: Double,
    market_cap: Long,
    price_change_percentage_24h: Double?,
    sparkline: String?,
  ) {
    driver.execute(-940_514_702, """
        |INSERT INTO CoinData (id, name, symbol, current_price, market_cap, price_change_percentage_24h, sparkline)
        |VALUES (?, ?, ?, ?, ?, ?, ?)
        """.trimMargin(), 7) {
          bindString(0, id)
          bindString(1, name)
          bindString(2, symbol)
          bindDouble(3, current_price)
          bindLong(4, market_cap)
          bindDouble(5, price_change_percentage_24h)
          bindString(6, sparkline)
        }
    notifyQueries(-940_514_702) { emit ->
      emit("CoinData")
    }
  }

  public fun removeAllCoinData() {
    driver.execute(1_917_321_210, """DELETE FROM CoinData""", 0)
    notifyQueries(1_917_321_210) { emit ->
      emit("CoinData")
    }
  }

  public fun insertSearchCoin(
    id: String,
    name: String,
    api_symbol: String,
    symbol: String,
    market_cap_rank: Long,
    thumb: String,
    large: String,
  ) {
    driver.execute(-756_077_776, """
        |INSERT INTO SearchCoin (id, name, api_symbol, symbol, market_cap_rank, thumb, large)
        |VALUES (?, ?, ?, ?, ?, ?, ?)
        """.trimMargin(), 7) {
          bindString(0, id)
          bindString(1, name)
          bindString(2, api_symbol)
          bindString(3, symbol)
          bindLong(4, market_cap_rank)
          bindString(5, thumb)
          bindString(6, large)
        }
    notifyQueries(-756_077_776) { emit ->
      emit("SearchCoin")
    }
  }

  public fun removeAllSearchCoins() {
    driver.execute(984_338_619, """DELETE FROM SearchCoin""", 0)
    notifyQueries(984_338_619) { emit ->
      emit("SearchCoin")
    }
  }

  public fun insertTrendingCoin(
    id: String,
    coin_id: Long,
    name: String,
    symbol: String,
    market_cap_rank: Long?,
    thumb: String?,
    small: String?,
    large: String?,
    slug: String?,
    price_btc: Double?,
    score: Long,
  ) {
    driver.execute(-791_037_331, """
        |INSERT INTO TrendingCoin (id, coin_id, name, symbol, market_cap_rank, thumb, small, large, slug, price_btc, score)
        |VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """.trimMargin(), 11) {
          bindString(0, id)
          bindLong(1, coin_id)
          bindString(2, name)
          bindString(3, symbol)
          bindLong(4, market_cap_rank)
          bindString(5, thumb)
          bindString(6, small)
          bindString(7, large)
          bindString(8, slug)
          bindDouble(9, price_btc)
          bindLong(10, score)
        }
    notifyQueries(-791_037_331) { emit ->
      emit("TrendingCoin")
    }
  }

  public fun removeAllTrendingCoins() {
    driver.execute(-486_298_850, """DELETE FROM TrendingCoin""", 0)
    notifyQueries(-486_298_850) { emit ->
      emit("TrendingCoin")
    }
  }
}
