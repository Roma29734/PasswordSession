
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
    id("org.jetbrains.compose")
    alias(libs.plugins.sqlDelight)
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
            implementation(libs.russhwolfSettongs)
            implementation(libs.russhwolfMultiplatform)
            implementation(libs.logging)

            // ton
            implementation(libs.tonTvm)
            implementation(libs.tonTlb)
            implementation(libs.tonTlbHashMap)
            implementation(libs.tonTlbBlock)
            implementation(libs.tonCrypto)
            implementation(libs.tonAdnl)
            implementation(libs.tonContract)
            implementation(libs.tonLiteClient)

        }
        jvmMain.dependencies {
            implementation(libs.sqlDelightJvm)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            implementation(libs.sqlDelightAndroid)
            implementation(libs.androidx.activity.compose)
        }
        iosMain.dependencies {
            implementation(libs.sqlDelightNative)
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

sqldelight {
    databases {
        create("Database") {
            packageName.set("com.pass.word.session.cache")
            deriveSchemaFromMigrations = true
        }
    }
}