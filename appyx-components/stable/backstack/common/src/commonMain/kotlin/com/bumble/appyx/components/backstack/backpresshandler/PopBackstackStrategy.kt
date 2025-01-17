package com.bumble.appyx.components.backstack.backpresshandler

import androidx.compose.animation.core.AnimationSpec
import com.bumble.appyx.components.backstack.BackStackModel
import com.bumble.appyx.components.backstack.operation.Pop
import com.bumble.appyx.interactions.core.model.backpresshandlerstrategies.BaseBackPressHandlerStrategy
import com.bumble.appyx.mapState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

class PopBackstackStrategy<InteractionTarget : Any>(
    val scope: CoroutineScope,
    val animationSpec: AnimationSpec<Float>? = null
) :
    BaseBackPressHandlerStrategy<InteractionTarget, BackStackModel.State<InteractionTarget>>() {

    override val canHandleBackPress: StateFlow<Boolean> by lazy {
        transitionModel.output.mapState(scope) { output ->
            output.currentTargetState.stashed.isNotEmpty()
        }
    }

    override fun handleBackPress(): Boolean {
        val pop = Pop<InteractionTarget>()
        //todo find a better way to check if operation is applicable
        return if (pop.isApplicable(transitionModel.output.value.currentTargetState)) {
            appyxComponent.operation(operation = Pop(), animationSpec)
            true
        } else false
    }
}
