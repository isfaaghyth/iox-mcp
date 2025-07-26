package app.isfa.spendings.util

import platform.UIKit.UIDevice

actual fun platform() = object : Platform {

    override val name: String
        get() = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion

    override val ipAddress: String
        get() = "127.0.0.1"
}