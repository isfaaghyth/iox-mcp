package app.isfa.spendings.util

// https://stackoverflow.com/a/72730483
fun Number.formatDecimalSeparator(): String {
    return this
        .toLong()
        .or(0)
        .toString()
        .reversed()
        .chunked(3)
        .joinToString(",")
        .reversed()
}