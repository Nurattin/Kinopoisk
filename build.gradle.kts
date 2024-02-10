// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://storage.googleapis.com/r8-releases/raw")
        }

        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            authentication {
                create<BasicAuthentication>("basic")
            }
            credentials {
                username = "mapbox"
                password =
                    "sk.eyJ1Ijoiamh1dXVpayIsImEiOiJjbHBmdGVyNW4xcXA1MmhybDJsY2FodGdtIn0.M4B2ULGkzYdTJKBmnOcWOA"
            }
        }
    }
    dependencies {
        classpath(libs.kotlin.serialization)
        classpath(libs.kotlin.gradle)
        classpath(libs.google.dagger)
        classpath(libs.r8)
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