plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
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

        val commonMain by getting{
            dependencies{

            }
        }
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
    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            res.srcDirs("src/commonMain/resources") // <============= here
        }
    }
}