import SwiftUI
import Shared

struct TrendingCoinView: View {
    @ObservedObject private(set) var viewModel: TrendingCoinViewModel

    var body: some View {
        NavigationView {
            VStack {
                // Display trending coins
                listView()
            }
            .navigationBarTitle("Trending Coins")
            .navigationBarItems(trailing:
                Button(action: {
                    viewModel.loadTrendingCoins(forceReload: true)
                }) {
                    Image(systemName: "arrow.clockwise")
                }
            )
        }
    }

    private func listView() -> AnyView {
        switch viewModel.trendingCoins {
        case .loading:
            return AnyView(Text("Loading...").multilineTextAlignment(.center))
        case .result(let coins):
            return AnyView(List(coins.map { IdentifiableTrendingCoin(coin: $0) }) { coin in
                TrendingCoinRow(trendingCoin: coin.coin)
            })
        case .error(let description):
            return AnyView(Text(description).multilineTextAlignment(.center))
        }
    }
}

extension TrendingCoinView {
    @MainActor
    class TrendingCoinViewModel: ObservableObject {
        @Published var trendingCoins = LoadableTrendingCoins.loading
        private let helper: KoinHelper

        init(helper: KoinHelper) {
            self.helper = helper
            self.loadTrendingCoins(forceReload: false)
        }

        func loadTrendingCoins(forceReload: Bool) {
            Task {
                do {
                    self.trendingCoins = .loading
                    let coins = try await helper.getTrendingCoins(forceReload: forceReload)
                    self.trendingCoins = .result(coins)
                } catch {
                    self.trendingCoins = .error(error.localizedDescription)
                }
            }
        }
    }

    enum LoadableTrendingCoins {
        case loading
        case result([TrendingCoin])
        case error(String)
    }

    struct IdentifiableTrendingCoin: Identifiable {
        let id: String
        let coin: TrendingCoin

        init(coin: TrendingCoin) {
            self.coin = coin
            self.id = coin.id
        }
    }
}
