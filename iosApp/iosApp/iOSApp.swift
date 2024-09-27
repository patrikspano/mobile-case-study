import SwiftUI
import Shared

@main
struct iOSApp: App {
    @State private var showWelcomeScreen = !UserDefaults.standard.bool(forKey: "hasSeenWelcomeScreen") // Check if welcome screen should be shown

    init() {
        KoinHelperKt.doInitKoin() // Initialize Koin
    }
    
    var body: some Scene {
        WindowGroup {
            if showWelcomeScreen {
                WelcomeScreen(showWelcomeScreen: $showWelcomeScreen)
                    .onDisappear {
                        UserDefaults.standard.set(true, forKey: "hasSeenWelcomeScreen")
                    }
            } else {
                ContentView() // Your main content view
            }
        }
    }
}
