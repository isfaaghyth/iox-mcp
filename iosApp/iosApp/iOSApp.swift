import SwiftUI
import KmpSpendings

@main
struct iOSApp: App {
   private var receiver = ImageIntentReceiver()

    init() {
       ProvidersKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
                .onOpenURL { url in
                   receiver.handleSharedImages(urls: [url])
                }
        }
    }
}
