import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    id("com.varabyte.kobweb.application")
    id("com.varabyte.kobwebx.markdown")
}

group = "helloworld"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    configAsKobwebApplication(includeServer = true)
    jvmToolchain(11)

    @Suppress("UNUSED_VARIABLE") // Suppress spurious warnings about sourceset variables not being used
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
            }
        }
        val jsMain by getting {
            dependencies {
                implementation("com.varabyte.kobweb:kobweb-core")
                implementation("com.varabyte.kobweb:kobweb-silk")
                implementation("com.varabyte.kobweb:kobweb-silk-icons-fa")
                implementation("com.varabyte.kobwebx:kobwebx-markdown")
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("com.varabyte.kobweb:kobweb-api")
            }
        }
    }
}