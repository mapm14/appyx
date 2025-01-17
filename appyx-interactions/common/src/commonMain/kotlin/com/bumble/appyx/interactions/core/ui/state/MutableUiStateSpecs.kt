package com.bumble.appyx.interactions.core.ui.state

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
@Suppress("Unused")
annotation class MutableUiStateSpecs(
    val animationMode: AnimationMode = AnimationMode.CONCURRENT
) {
    enum class AnimationMode {
        CONCURRENT, SEQUENTIAL
    }
}
