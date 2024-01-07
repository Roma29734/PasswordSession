plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.sqlDelight).apply(false)
    id("org.jetbrains.compose")
}

kotlin {
    tasks.withType<Jar> { duplicatesStrategy = DuplicatesStrategy.EXCLUDE }

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
            implementation(libs.decompose)
            implementation(libs.kotlinx.serialization.json)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            implementation(compose.materialIconsExtended)
            // more dep
            implementation(libs.kotlinXDataTime)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

    }
}

android {
    namespace = "com.pass.word.session"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    sourceSets["main"].apply {
        res.srcDirs("src/androidMain/res", "src/commonMain/resources")
    }
}
