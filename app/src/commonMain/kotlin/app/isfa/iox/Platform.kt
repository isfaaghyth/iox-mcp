package app.isfa.iox

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform