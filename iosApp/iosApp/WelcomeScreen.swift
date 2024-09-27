import SwiftUI
import Shared

struct WelcomeScreen: View {
    @Binding var showWelcomeScreen: Bool
    
    var body: some View {
        VStack {
            Spacer()
            Text("Welcome to Coin Peek")
                .font(.largeTitle)
                .fontWeight(.bold)
                .multilineTextAlignment(.center)
                .padding(.horizontal, 48)
            Spacer()
            
            VStack(spacing: 24) {
                FeatureCell(image: "chart.line.uptrend.xyaxis", title: "Trending Coins", subtitle: "Get information about top 15 trending coins in the last 24 hours.", color: .green)
                
                FeatureCell(image: "magnifyingglass", title: "Seach for Coins", subtitle: "Search for a coin by name or symbol", color: .blue)
                
                FeatureCell(image: "star", title: "Favorite Coins", subtitle: "Get information about your favorite coins.", color: .yellow)
            }
            .padding(.leading)
            
            Spacer()
            Spacer()
            
            Button(action: { self.showWelcomeScreen = false }) {
                HStack {
                    Spacer()
                    Text("Continue")
                        .font(.headline)
                        .foregroundColor(.white)
                    Spacer()
                }
            }
            .frame(height: 50)
            .background(Color.blue)
            .cornerRadius(15)
        }
        .padding()
    }
}

struct WelcomeScreen_Previews: PreviewProvider {
    static var previews: some View {
        WelcomeScreen(showWelcomeScreen: .constant(true))
    }
}
