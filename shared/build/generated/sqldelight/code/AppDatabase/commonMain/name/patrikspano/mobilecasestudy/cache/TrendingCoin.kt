package name.patrikspano.mobilecasestudy.cache

import kotlin.Double
import kotlin.Long
import kotlin.String

public data class TrendingCoin(
  public val id: String,
  public val coin_id: Long,
  public val name: String,
  public val symbol: String,
  public val market_cap_rank: Long?,
  public val thumb: String?,
  public val small: String?,
  public val large: String?,
  public val slug: String?,
  public val price_btc: Double?,
  public val score: Long,
)
