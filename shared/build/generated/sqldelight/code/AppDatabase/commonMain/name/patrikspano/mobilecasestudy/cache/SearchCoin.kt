package name.patrikspano.mobilecasestudy.cache

import kotlin.Long
import kotlin.String

public data class SearchCoin(
  public val id: String,
  public val name: String,
  public val api_symbol: String,
  public val symbol: String,
  public val market_cap_rank: Long,
  public val thumb: String,
  public val large: String,
)
