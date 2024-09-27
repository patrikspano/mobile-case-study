package name.patrikspano.mobilecasestudy.cache.shared

import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.AfterVersion
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import kotlin.Long
import kotlin.Unit
import kotlin.reflect.KClass
import name.patrikspano.mobilecasestudy.cache.AppDatabase
import namepatrikspanomobilecasestudy.cache.AppDatabaseQueries

internal val KClass<AppDatabase>.schema: SqlSchema<QueryResult.Value<Unit>>
  get() = AppDatabaseImpl.Schema

internal fun KClass<AppDatabase>.newInstance(driver: SqlDriver): AppDatabase =
    AppDatabaseImpl(driver)

private class AppDatabaseImpl(
  driver: SqlDriver,
) : TransacterImpl(driver), AppDatabase {
  override val appDatabaseQueries: AppDatabaseQueries = AppDatabaseQueries(driver)

  public object Schema : SqlSchema<QueryResult.Value<Unit>> {
    override val version: Long
      get() = 1

    override fun create(driver: SqlDriver): QueryResult.Value<Unit> {
      driver.execute(null, """
          |CREATE TABLE CoinData (
          |    id TEXT PRIMARY KEY,
          |    name TEXT NOT NULL,
          |    symbol TEXT NOT NULL,
          |    image TEXT NOT NULL,
          |    current_price REAL NOT NULL,
          |    market_cap INTEGER NOT NULL,
          |    price_change_percentage_24h REAL,
          |    sparkline TEXT,
          |    is_favorite INTEGER NOT NULL DEFAULT 0
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE SearchCoin (
          |    id TEXT PRIMARY KEY,
          |    name TEXT NOT NULL,
          |    api_symbol TEXT NOT NULL,
          |    symbol TEXT NOT NULL,
          |    market_cap_rank INTEGER NOT NULL,
          |    thumb TEXT NOT NULL,
          |    large TEXT NOT NULL
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE TrendingCoin (
          |    id TEXT PRIMARY KEY,
          |    coin_id INTEGER NOT NULL,
          |    name TEXT NOT NULL,
          |    symbol TEXT NOT NULL,
          |    market_cap_rank INTEGER,
          |    thumb TEXT,
          |    small TEXT,
          |    large TEXT,
          |    slug TEXT,
          |    price_btc REAL,
          |    score INTEGER NOT NULL
          |)
          """.trimMargin(), 0)
      return QueryResult.Unit
    }

    override fun migrate(
      driver: SqlDriver,
      oldVersion: Long,
      newVersion: Long,
      vararg callbacks: AfterVersion,
    ): QueryResult.Value<Unit> = QueryResult.Unit
  }
}
