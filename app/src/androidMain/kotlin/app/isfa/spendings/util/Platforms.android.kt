package app.isfa.spendings.util

import android.os.Build

actual fun platform() = object : Platform {

    override val name: String
        get() = "Android ${Build.VERSION.SDK_INT}"

    override val ipAddress: String
        get() = "10.0.2.2"
}