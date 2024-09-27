import SwiftUI
import Shared

struct TrendingCoinRow: View {
    var trendingCoin: TrendingCoin

    var body: some View {
        HStack {
            AsyncImage(url: URL(string: trendingCoin.small)) { image in
                image
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width: 40, height: 40)
                    .clipShape(Circle())
            } placeholder: {
                Circle()
                    .fill(Color.gray.opacity(0.3))
                    .frame(width: 40, height: 40)
            }
            
            VStack(alignment: .leading, spacing: 5) {
                Text(trendingCoin.name)
                    .font(.system(size: 18))
                    .bold()
                Text("\(trendingCoin.symbol)")
                    .font(.subheadline)
                    .foregroundColor(.gray)
            }
            
            Spacer()
            
            VStack(alignment: .trailing, spacing: 5) {
                Text("Price in BTC")
                    .font(.subheadline)
                    .foregroundColor(.gray)
                Text("\(trendingCoin.priceBtc, specifier: "%.8f") $")
                    .font(.subheadline)
            }
        }
        .padding()
    }
}

struct TrendingCoinRow_Previews: PreviewProvider {
    static var previews: some View {
        let mockCoin = TrendingCoin(
            id: "bitcoin",
            coinId: 1,
            name: "Bitcoin",
            symbol: "BTC",
            marketCapRank: 1,
            thumb: "https://assets.coingecko.com/coins/images/1/thumb/bitcoin.png",
            small: "https://assets.coingecko.com/coins/images/1/small/bitcoin.png",
            large: "https://assets.coingecko.com/coins/images/1/large/bitcoin.png",
            slug: "bitcoin",
            priceBtc: 64473.34,
            score: 1
        )


        TrendingCoinRow(trendingCoin: mockCoin)
            .previewLayout(.sizeThatFits) // Adjusts to the content size
            .padding()
    }
}
