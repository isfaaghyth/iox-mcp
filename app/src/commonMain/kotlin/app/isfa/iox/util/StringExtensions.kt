package app.isfa.iox.util

fun String.cleanUp(): String {
    return this
        .replace("```json", "")
        .replace("```", "")
        .replace("\\n", "")
        .replace("\\", "")
        .trim()
}