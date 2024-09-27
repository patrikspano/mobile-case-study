import SwiftUI
import Shared

struct FavoriteCoinRow: View {
    let coin: CoinData

    var body: some View {
        HStack {
            VStack(alignment: .leading) {
                HStack {
                    AsyncImage(url: URL(string: coin.image)) { image in
                        image.resizable()
                    } placeholder: {
                        ProgressView()
                    }
                    .frame(width: 50, height: 50)
                    .clipShape(Circle())

                    VStack(alignment: .leading) {
                        Text(coin.name)
                            .font(.headline)
                        Text(coin.symbol.uppercased())
                            .font(.subheadline)
                    }
                    Spacer()
                }

                VStack(alignment: .leading) {
                    Text("$\(coin.currentPrice, specifier: "%.2f")")
                        .font(.title2)
                    Text("24h: \(coin.priceChangePercentage24h, specifier: "%.2f")%")
                        .font(.subheadline)
                        .foregroundColor(coin.priceChangePercentage24h >= 0 ? .green : .red)
                }
            }
            .padding()
        }
    }
}

struct GraphView: View {
    let data: [Double]

    var body: some View {
        Rectangle()
            .fill(Color.gray)
            .cornerRadius(5)
    }
}
