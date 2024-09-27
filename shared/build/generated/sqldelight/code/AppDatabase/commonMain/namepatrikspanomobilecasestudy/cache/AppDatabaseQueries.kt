package namepatrikspanomobilecasestudy.cache

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
    image: String,
    current_price: Double,
    market_cap: Long,
    price_change_percentage_24h: Double?,
    sparkline: String?,
    is_favorite: Long,
  ) -> T): Query<T> = Query(-562_227_762, arrayOf("CoinData"), driver, "AppDatabase.sq",
      "selectAllCoinData",
      "SELECT CoinData.id, CoinData.name, CoinData.symbol, CoinData.image, CoinData.current_price, CoinData.market_cap, CoinData.price_change_percentage_24h, CoinData.sparkline, CoinData.is_favorite FROM CoinData WHERE is_favorite = 1") {
      cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!,
      cursor.getDouble(4)!!,
      cursor.getLong(5)!!,
      cursor.getDouble(6),
      cursor.getString(7),
      cursor.getLong(8)!!
    )
  }

  public fun selectAllCoinData(): Query<CoinData> = selectAllCoinData { id, name, symbol, image,
      current_price, market_cap, price_change_percentage_24h, sparkline, is_favorite ->
    CoinData(
      id,
      name,
      symbol,
      image,
      current_price,
      market_cap,
      price_change_percentage_24h,
      sparkline,
      is_favorite
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
  ) -> T): Query<T> = Query(1_883_437_671, arrayOf("SearchCoin"), driver, "AppDatabase.sq",
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
  ) -> T): Query<T> = Query(259_463_626, arrayOf("TrendingCoin"), driver, "AppDatabase.sq",
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
    image: String,
    current_price: Double,
    market_cap: Long,
    price_change_percentage_24h: Double?,
    sparkline: String?,
    is_favorite: Long,
  ) {
    driver.execute(-1_978_173_274, """
        |INSERT INTO CoinData (id, name, symbol, image ,current_price, market_cap, price_change_percentage_24h, sparkline, is_favorite )
        |VALUES (?, ?, ?, ?, ?,?, ?, ?, ?)
        """.trimMargin(), 9) {
          bindString(0, id)
          bindString(1, name)
          bindString(2, symbol)
          bindString(3, image)
          bindDouble(4, current_price)
          bindLong(5, market_cap)
          bindDouble(6, price_change_percentage_24h)
          bindString(7, sparkline)
          bindLong(8, is_favorite)
        }
    notifyQueries(-1_978_173_274) { emit ->
      emit("CoinData")
    }
  }

  public fun removeAllCoinData() {
    driver.execute(-89_567_930, """DELETE FROM CoinData""", 0)
    notifyQueries(-89_567_930) { emit ->
      emit("CoinData")
    }
  }

  public fun updateCoinData(is_favorite: Long, id: String) {
    driver.execute(1_863_835_318, """
        |UPDATE CoinData
        |SET is_favorite = ?
        |WHERE id = ?
        """.trimMargin(), 2) {
          bindLong(0, is_favorite)
          bindString(1, id)
        }
    notifyQueries(1_863_835_318) { emit ->
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
    driver.execute(-1_513_552_796, """
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
    notifyQueries(-1_513_552_796) { emit ->
      emit("SearchCoin")
    }
  }

  public fun removeAllSearchCoins() {
    driver.execute(-305_270_801, """DELETE FROM SearchCoin""", 0)
    notifyQueries(-305_270_801) { emit ->
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
    driver.execute(1_419_908_769, """
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
    notifyQueries(1_419_908_769) { emit ->
      emit("TrendingCoin")
    }
  }

  public fun removeAllTrendingCoins() {
    driver.execute(1_444_597_074, """DELETE FROM TrendingCoin""", 0)
    notifyQueries(1_444_597_074) { emit ->
      emit("TrendingCoin")
    }
  }
}
