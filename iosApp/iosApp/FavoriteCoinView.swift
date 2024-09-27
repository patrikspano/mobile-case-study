import SwiftUI
import Shared

struct FavoriteCoinView: View {
    @ObservedObject private(set) var viewModel: FavoriteCoinViewModel
    @State private var searchText = ""
    @State private var searchPerformed = false // Flag to track if search was performed

    var body: some View {
        NavigationView {
            VStack {
                // Search bar with clear button
                HStack {
                    TextField("Search Coins", text: $searchText, onEditingChanged: { isEditing in
                        if isEditing && !searchText.isEmpty {
                            viewModel.performSearch(query: searchText)
                            searchPerformed = true
                        } else {
                            viewModel.clearSearchResults()
                            searchPerformed = false
                        }
                    }, onCommit: {
                        viewModel.performSearch(query: searchText)
                        searchPerformed = true
                    })
                    .padding(10)
                    .background(Color(.systemGray6))
                    .cornerRadius(8)
                    .padding(.horizontal, 16)

                    // Clear button
                    if !searchText.isEmpty {
                        Button(action: {
                            clearSearch()
                        }) {
                            Image(systemName: "xmark.circle")
                                .foregroundColor(.gray)
                        }
                        .padding(.trailing, 16)
                    }
                }
                .padding(.vertical, 8)
                .background(Color(.systemBackground)) // Stick the search bar visually

                // Check if there are no favorite coins to show the centered message
                if !searchPerformed && viewModel.favoriteCoins.isEmpty {
                    Spacer()
                    Text("No favorite coins yet 😕 Add some using the search bar.")
                        .multilineTextAlignment(.center)
                        .font(.headline)
                        .foregroundColor(.secondary)
                        .padding()
                    Spacer()
                } else {
                    // Display search results or favorite coins
                    listView()
                }
            }
            .navigationBarTitle("Favorite Coins")
            .navigationBarItems(trailing:
                Button(action: {
                    viewModel.loadFavoriteCoins(forceReload: true)
                }) {
                    Image(systemName: "arrow.clockwise")
                }
            )
            .onAppear {
                viewModel.loadFavoriteCoins(forceReload: false)
            }
        }
    }

    private func clearSearch() {
        // Clear the search text, suggestions, and reset the search flag
        searchText = ""
        searchPerformed = false
        viewModel.clearSearchResults()
    }

    private func listView() -> some View {
        List {
            // Show search results when a search is performed
            if searchPerformed && !viewModel.searchResults.isEmpty {
                ForEach(viewModel.searchResults.map { IdentifiableSearchCoin(coin: $0) }) { coin in
                    // Clicking on the row will trigger the favorite action
                    SearchCoinRow(searchCoin: coin.coin)
                        .onTapGesture {
                            viewModel.addCoinToFavorites(coin.coin)
                        }
                        .padding(.vertical, 4)
                }
            } else if searchPerformed && viewModel.searchResults.isEmpty {
                // Display "No match found" only after a search is performed and no results are found
                VStack {
                    Spacer()
                    Text("No matching results found")
                        .font(.headline)
                        .foregroundColor(.secondary)
                    Spacer()
                }
            } else {
                // Display favorite coins if search is not performed
                ForEach(viewModel.favoriteCoins.map { IdentifiableCoin(coin: $0) }) { coin in
                    FavoriteCoinRow(coin: coin.coin)
                        .swipeActions {
                            Button(role: .destructive) {
                                viewModel.removeCoinFromFavorites(coin.coin)
                            } label: {
                                Label("Remove", systemImage: "trash")
                            }
                        }
                }
            }
        }
        .listStyle(PlainListStyle())
    }
}

// Definition of IdentifiableCoin to wrap CoinData with Identifiable conformance
struct IdentifiableCoin: Identifiable {
    let id: String
    let coin: CoinData

    init(coin: CoinData) {
        self.coin = coin
        self.id = coin.id
    }
}

// Definition of IdentifiableSearchCoin to wrap SearchCoin with Identifiable conformance
struct IdentifiableSearchCoin: Identifiable {
    let id: String
    let coin: SearchCoin

    init(coin: SearchCoin) {
        self.coin = coin
        self.id = coin.id
    }
}

extension FavoriteCoinView {
    @MainActor
    class FavoriteCoinViewModel: ObservableObject {
        @Published var favoriteCoins: [CoinData] = []
        @Published var searchResults: [SearchCoin] = [] // To store search results
        private let helper: KoinHelper

        init(helper: KoinHelper) {
            self.helper = helper
            loadFavoriteCoins(forceReload: false)
        }

        func loadFavoriteCoins(forceReload: Bool) {
            Task {
                do {
                    // Fetch all CoinData and filter by isFavorite, correctly reloading coins
                    let allCoins = try await helper.getCoinData(forceReload: forceReload)
                    self.favoriteCoins = allCoins.filter { $0.isFavorite }
                } catch {
                    print("Error loading favorite coins: \(error.localizedDescription)")
                }
            }
        }

        func performSearch(query: String) {
            Task {
                do {
                    let coins = try await helper.getSearchCoins(query: query, forceReload: false)
                    self.searchResults = coins
                } catch {
                    print("Error performing search: \(error.localizedDescription)")
                    self.searchResults = []
                }
            }
        }

        func clearSearchResults() {
            self.searchResults = []
        }

        func addCoinToFavorites(_ coin: SearchCoin) {
            let coinData = CoinData(
                id: coin.id,
                name: coin.name,
                symbol: coin.symbol,
                image: coin.thumb,
                currentPrice: 0,
                marketCap: 0,
                priceChangePercentage24h: 0,
                sparkline: SparklineData(price: []),
                isFavorite: true
            )
            Task {
                do {
                    try await helper.updateFavoriteStatus(coin: coinData)
                    loadFavoriteCoins(forceReload: false) // Refresh favorites list
                } catch {
                    print("Error adding coin to favorites: \(error.localizedDescription)")
                }
            }
        }

        func removeCoinFromFavorites(_ coin: CoinData) {
            let updatedCoin = coin
            updatedCoin.isFavorite = false
            Task {
                do {
                    try await helper.updateFavoriteStatus(coin: updatedCoin)
                    loadFavoriteCoins(forceReload: false) // Reload the favorites list to reflect changes
                } catch {
                    print("Error removing coin from favorites: \(error.localizedDescription)")
                }
            }
        }
    }
}
