// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.kotlin.serialization)
        classpath(libs.kotlin.gradle)
        classpath(libs.google.dagger)
    }
}

plugins {
    alias(libs.plugins.jetbrains.android) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
}
tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}