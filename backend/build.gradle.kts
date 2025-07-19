import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinxSerialization)
}

kotlin {
    jvm {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        binaries {
            executable {
                mainClass = "MainKt"
            }
        }
    }

    sourceSets {
        jvmMain.dependencies {
            implementation(libs.kotlinx.coroutines)

            implementation("io.ktor:ktor-server-core:3.2.2")
            implementation("io.ktor:ktor-server-netty:3.2.2")
            implementation("io.ktor:ktor-server-cors:3.2.2")
            implementation("io.ktor:ktor-serialization-kotlinx-json:3.2.2")
            implementation("io.ktor:ktor-server-content-negotiation:3.2.2")
            implementation("ch.qos.logback:logback-classic:1.4.11") {
                exclude(group = "io.ktor", module = "ktor-client-core")
                exclude(group = "io.ktor", module = "ktor-serialization-kotlinx-json")
            }

            implementation(projects.app)
        }
    }
}
