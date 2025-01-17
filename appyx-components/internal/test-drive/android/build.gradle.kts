plugins {
    id("com.bumble.appyx.android.library")
    id("org.jetbrains.compose")
    id("appyx-screenshots")
}

appyx {
    namespace.set("com.bumble.appyx.components.internal.testdrive.android")
}

dependencies {
    implementation(project(":appyx-components:internal:test-drive:test-drive"))
    implementation(project(":appyx-interactions:android"))

}
