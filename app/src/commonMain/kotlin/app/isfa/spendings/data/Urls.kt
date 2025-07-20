package app.isfa.spendings.data

enum class Urls(val baseUrl: String) {
    /**
     * Gemini public API.
     */
    Gemini("https://generativelanguage.googleapis.com"),

    /**
     * 10.0.2.2 host to hit our local backend service in android emulator
     */
    LocalAndroidEmulator("http://10.0.2.2:8080"),

    /**
     * Common localhost
     */
    LocalMcp("http://0.0.0.0:8080")
}
