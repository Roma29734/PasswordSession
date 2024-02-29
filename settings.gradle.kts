enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    plugins {
        val composeVersion = extra["compose.version"] as String
        id("org.jetbrains.compose").version(composeVersion)
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

rootProject.name = "PasswordSession"
include(":androidApp")
include(":shared")


//enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
//pluginManagement {
//    repositories {
//        google()
//        gradlePluginPortal()
//        mavenCentral()
//        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
//    }
//    plugins {
//        val composeVersion = extra["compose.version"] as String
//        id("org.jetbrains.compose").version(composeVersion)
//    }
//}
//
//dependencyResolutionManagement {
//    repositories {
//        google()
//        mavenCentral()
//        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
//    }
//}
//
//rootProject.name = "PasswordSession"
//include(":androidApp")
//include(":shared")