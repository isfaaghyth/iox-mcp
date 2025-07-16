//
// Created by Muh Isfhani Ghiath on 16/07/25.
//

import Foundation
import MoneyMonitor

class ImageHandler: ObservableObject {

    @Published var receivedImages: [ImageIntentData] = []
    private var receiver: ImageIntentReceiver

    init() {
        receiver = ImageIntentReceiver { [weak self] data in
            DispatchQueue.main.async {
                self?.receivedImages.append(data)
            }
        }
    }

    func handleIncomingURL(_ url: URL) {
        let nsurl = NSURL(string: url.absoluteString)!
        receiver.handleSharedImages(urls: [nsurl])
    }
}