import SwiftUI
import Shared

struct SearchCoinRow: View {
    let searchCoin: SearchCoin
    @State private var isTapped: Bool = false // State to track tap effect

    var body: some View {
        HStack {
            AsyncImage(url: URL(string: searchCoin.thumb)) { image in
                image
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width: 20, height: 20)
                    .clipShape(Circle())
            } placeholder: {
                Circle()
                    .fill(Color.gray.opacity(0.3))
                    .frame(width: 20, height: 20)
            }

            // Center: Name
            Text(searchCoin.name)
                .font(.headline)
                .padding(.leading, 8)

            Spacer()

            // Right: Symbol
            Text(searchCoin.symbol.uppercased())
                .font(.subheadline)
                .foregroundColor(.secondary)
        }
        .padding(.vertical, 8)
        .padding(.horizontal, 16) // Ensures padding to match the entire row
        .background(
            RoundedRectangle(cornerRadius: 10)
                .fill(isTapped ? Color.gray.opacity(0.2) : Color.clear) // Highlight effect with rounded corners
        )
        .contentShape(Rectangle()) // Ensures the whole row is tappable
        .onTapGesture {
            isTapped = true // Trigger tap effect
            DispatchQueue.main.asyncAfter(deadline: .now() + 0.2) {
                withAnimation {
                    isTapped = false // Reset the tap effect after a brief moment
                }
            }
        }
    }
}

// Example usage in a list
struct SearchCoinRow_Previews: PreviewProvider {
    static var previews: some View {
        SearchCoinRow(searchCoin: SearchCoin(
            id: "1",
            name: "Bitcoin",
            apiSymbol: "btc",
            symbol: "BTC",
            marketCapRank: 1,
            thumb:"https://assets.coingecko.com/coins/images/1/thumb/bitcoin.png",
            large: "https://example.com/large.png"
        ))
        .previewLayout(.sizeThatFits)
    }
}
