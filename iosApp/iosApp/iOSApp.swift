import SwiftUI
import MoneyMonitor

@main
struct iOSApp: App {
    @StateObject private var imageHandler = ImageHandler()

    var body: some Scene {
        WindowGroup {
            ContentView(image: imageHandler)
        }
    }
}