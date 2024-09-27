package name.patrikspano.mobilecasestudy.cache

import app.cash.sqldelight.Transacter
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import kotlin.Unit
import name.patrikspano.mobilecasestudy.cache.shared.newInstance
import name.patrikspano.mobilecasestudy.cache.shared.schema
import namepatrikspanomobilecasestudy.cache.AppDatabaseQueries

public interface AppDatabase : Transacter {
  public val appDatabaseQueries: AppDatabaseQueries

  public companion object {
    public val Schema: SqlSchema<QueryResult.Value<Unit>>
      get() = AppDatabase::class.schema

    public operator fun invoke(driver: SqlDriver): AppDatabase =
        AppDatabase::class.newInstance(driver)
  }
}
