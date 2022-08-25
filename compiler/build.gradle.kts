plugins {
    kotlin("jvm")
    id("com.vanniktech.maven.publish")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(libs.bundles.ksp)
    compileOnly(project(":stub"))
}

mavenPublish {
    sonatypeHost = com.vanniktech.maven.publish.SonatypeHost.S01
}