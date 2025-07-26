package app.isfa.spendings.util

interface Platform {
    val name: String
    val ipAddress: String
}

expect fun platform(): Platform
