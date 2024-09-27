package namepatrikspanomobilecasestudy.cache

import kotlin.Double
import kotlin.Long
import kotlin.String

public data class CoinData(
  public val id: String,
  public val name: String,
  public val symbol: String,
  public val image: String,
  public val current_price: Double,
  public val market_cap: Long,
  public val price_change_percentage_24h: Double?,
  public val sparkline: String?,
  public val is_favorite: Long,
)
