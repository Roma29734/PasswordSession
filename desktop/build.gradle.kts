import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    id("org.jetbrains.compose")
}

group = "com.pass.word.session.desktop"
version = "1.0.0"

kotlin {
    jvm {
        withJava()
        compilations.all {
            kotlinOptions.jvmTarget = "17"
        }
    }

    sourceSets {
        val jvmMain by getting {
            kotlin.srcDirs("src/jvmMain/kotlin")
            dependencies {

                implementation(projects.shared)

                implementation(compose.desktop.currentOs)
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material3)
                api(compose.ui)
                api(compose.materialIconsExtended)
                // decompose
                implementation(libs.decompose)
                implementation(libs.decomposeJetbrains)
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "PasswordSession"
            packageVersion = "1.0.0"
            windows {
                iconFile.set(project.file("icon.ico"))
            }
            macOS {
                bundleID = "com.pass.word.session.desktop"
            }
        }
    }
}