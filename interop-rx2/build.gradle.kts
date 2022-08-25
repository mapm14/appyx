plugins {
    id("com.android.library")
    id("kotlin-android")
    id("appyx-publish-android")
    id("appyx-lint")
    id("appyx-detekt")
}

android {
    namespace = "com.bumble.appyx.interop.rx2"
    compileSdk = 32

    defaultConfig {
        minSdk = 21
        targetSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }
}

dependencies {
    implementation(project(":core"))

    implementation(libs.androidx.lifecycle.java8)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.rxjava2)
    implementation(libs.rxrelay)

    testImplementation(libs.junit.api)
    testImplementation(libs.junit.params)
    testRuntimeOnly(libs.junit.engine)
}
