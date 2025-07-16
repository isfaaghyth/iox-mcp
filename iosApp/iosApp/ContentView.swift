import UIKit
import SwiftUI
import MoneyMonitor

struct ComposeView: UIViewControllerRepresentable {

    let image: ImageHandler

    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController(receivedImages: image.receivedImages)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    let image: ImageHandler

    var body: some View {
        ComposeView(image: image)
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}



