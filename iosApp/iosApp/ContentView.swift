import SwiftUI
import Shared

struct ContentView: View {
    var body: some View {
        TabView {
            TrendingCoinView(viewModel: TrendingCoinView.TrendingCoinViewModel(helper: KoinHelper()))
                .tabItem {
                    Label("Trending", systemImage: "chart.line.uptrend.xyaxis")
                }

            FavoriteCoinView(viewModel: FavoriteCoinView.FavoriteCoinViewModel(helper: KoinHelper()))
                .tabItem {
                    Label("Favorites", systemImage: "star.fill")
                }
        }
    }
}
