plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.shadowPlugin)
    application
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

application {
    mainClass = "app.isfa.ioxmcp.MainKt"
}

dependencies {
    implementation(libs.kotlinmcp) {
        exclude(group = "org.jetbrains.compose.ui", module = "ui-desktop")
    }
    implementation(projects.app)
    implementation(libs.koin.core)
}

tasks.shadowJar {
    archiveFileName.set("mcp-server.jar")
    archiveClassifier.set("")
    manifest {
        attributes["Main-Class"] = "app.isfa.ioxmcp.MainKt"
    }
}
