plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    id("org.jetbrains.compose")
}

android {
    namespace = "com.pass.word.session.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.pass.word.session.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    android.signingConfigs {
        create("release") {
            storeFile = file("my-release-key.jks")
            storePassword = "12345678"
            keyAlias = "my-key-alias"
            keyPassword = "12345678"
            storeType = "jks"
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(projects.shared)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.androidx.activity.compose)
    debugImplementation(libs.compose.ui.tooling)
//    decompose
    implementation(libs.decomposeJetpack)
    implementation(libs.decompose)

    implementation(libs.browserAndroid)

    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.30.1")
}