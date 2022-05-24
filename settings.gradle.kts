pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    plugins {
        id("com.google.devtools.ksp") version "1.6.21-1.0.5"
        id("com.android.library") version "7.2.0"
        id("org.jetbrains.kotlin.android") version "1.6.21"
    }
}
enableFeaturePreview("VERSION_CATALOGS")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
include(":app")
include(":api")
include(":compiler")
rootProject.name = "Compass"
include(":anno")
include(":sample_one")
include(":stub")
