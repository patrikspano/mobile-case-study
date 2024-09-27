import SwiftUI
import Shared

struct FavoriteCoinView: View {
    @ObservedObject private(set) var viewModel: FavoriteCoinViewModel
    @State private var searchText = ""

    var body: some View {
        NavigationView {
            VStack {
                // Search bar with clear button
                HStack {
                    TextField("Search Coins", text: $searchText, onEditingChanged: { isEditing in
                        if isEditing && !searchText.isEmpty {
                            viewModel.performSearch(query: searchText)
                        } else {
                            clearSearch()
                        }
                    }, onCommit: {
                        viewModel.performSearch(query: searchText)
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
                .background(Color(.systemBackground))

                // Check if there are no favorite coins to show the centered message
                if !viewModel.searchPerformed && viewModel.favoriteCoins.isEmpty {
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
        viewModel.clearSearchResults()
    }

    private func listView() -> some View {
        List {
            // Show search results when a search is performed
            if viewModel.searchPerformed && !viewModel.searchResults.isEmpty {
                ForEach(viewModel.searchResults.map { IdentifiableSearchCoin(coin: $0) }) { coin in
                    ZStack {
                        SearchCoinRow(searchCoin: coin.coin)
                            .padding(.vertical, 4)
                        
                        Color.clear
                            .contentShape(Rectangle())
                            .onTapGesture {
                                print("Tapped on coin: \(coin.coin.name)")
                                viewModel.addCoinToFavorites(coin.coin)
                            }
                    }
                }
            } else if viewModel.searchPerformed && viewModel.searchResults.isEmpty {
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
        @Published var searchResults: [SearchCoin] = []
        @Published var searchPerformed: Bool = false
        private let helper: KoinHelper

        init(helper: KoinHelper) {
            self.helper = helper
            loadFavoriteCoins(forceReload: false)
        }

        // Correct usage of getCoinData to load favorite coins
        func loadFavoriteCoins(forceReload: Bool) {
            Task {
                do {
                    // Use the helper to fetch coin data
                    let allCoins = try await helper.getCoinData(forceReload: forceReload)
                    // Filter only the favorite coins
                    self.favoriteCoins = allCoins.filter { $0.isFavorite }
                    print("Favorite coins loaded: \(favoriteCoins)") // Debug statement
                } catch {
                    print("Error loading favorite coins: \(error.localizedDescription)")
                }
            }
        }

        func performSearch(query: String) {
            Task {
                do {
                    let coins = try await helper.getSearchCoins(query: query, forceReload: false)
                    self.searchResults = coins.filter { coin in
                        coin.symbol.lowercased() == query.lowercased() || coin.name.lowercased().contains(query.lowercased())
                    }
                    self.searchPerformed = true
                } catch {
                    print("Error performing search: \(error.localizedDescription)")
                    self.searchResults = []
                    self.searchPerformed = true
                }
            }
        }

        func clearSearchResults() {
            self.searchResults = []
            self.searchPerformed = false
        }

        func addCoinToFavorites(_ coin: SearchCoin) {
            // Create a CoinData object with the favorite status set to true
            let coinData = CoinData(
                id: coin.id,
                name: coin.name,
                symbol: coin.symbol,
                image: coin.thumb,
                currentPrice: 0, // Adjust with the correct price if available
                marketCap: 0,    // Adjust with the correct market cap if available
                priceChangePercentage24h: 0, // Adjust if needed
                sparkline: SparklineData(price: []), // Replace with actual data if available
                isFavorite: true // Set favorite status to true
            )
            Task {
                do {
                    // Update the favorite status in the database
                    print("Attempting to add coin to favorites: \(coinData.id)")
                    try await helper.updateFavoriteStatus(coin: coinData)
                    // Reload the favorite coins to reflect the updated status
                    loadFavoriteCoins(forceReload: false)
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
