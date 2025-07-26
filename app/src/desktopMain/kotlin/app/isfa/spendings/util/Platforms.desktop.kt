package app.isfa.spendings.util

actual fun platform() = object : Platform {

    override val name: String
        get() = "Desktop"

    override val ipAddress: String
        get() = "0.0.0.0"
}