package com.bumble.appyx.interactions.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumble.appyx.interactions.core.model.BaseInteractionModel
import com.bumble.appyx.interactions.core.ui.context.TransitionBounds
import com.bumble.appyx.interactions.core.ui.context.UiContext
import com.bumble.appyx.interactions.core.ui.output.ElementUiModel
import kotlin.math.roundToInt


@Composable
fun <NavTarget : Any, NavState : Any> Children(
    interactionModel: BaseInteractionModel<NavTarget, NavState>,
    modifier: Modifier = Modifier,
    element: @Composable (ElementUiModel<NavTarget>) -> Unit = {
        Element(elementUiModel = it)
    },
) {
    val density = LocalDensity.current
    val frames = interactionModel.uiModels.collectAsState(listOf())
    val coroutineScope = rememberCoroutineScope()
    val screenWidthPx = (LocalConfiguration.current.screenWidthDp * density.density).roundToInt()
    val screenHeightPx = (LocalConfiguration.current.screenHeightDp * density.density).roundToInt()
    var uiContext by remember { mutableStateOf<UiContext?>(null) }

    LaunchedEffect(uiContext) {
        uiContext?.let {  interactionModel.updateContext(it) }
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .composed {
                val clipToBounds by interactionModel.clipToBounds.collectAsState()
                if (clipToBounds) {
                    clipToBounds()
                } else {
                    this
                }
            }
            .onPlaced {
                uiContext = UiContext(
                    TransitionBounds(
                        density = density,
                        widthPx = it.size.width,
                        heightPx = it.size.height,
                        containerBoundsInRoot = it.boundsInRoot(),
                        screenWidthPx = screenWidthPx,
                        screenHeightPx = screenHeightPx
                    ),
                    coroutineScope
                )
            }
    ) {
        frames.value.forEach { frameModel ->
            key(frameModel.element.id) {
                frameModel.animationContainer()
                val isVisible by frameModel.visibleState.collectAsState()
                if (isVisible) {
                    element.invoke(frameModel)
                }
            }
        }
    }
}

@Composable
fun Element(
    color: Color? = Color.Unspecified,
    elementUiModel: ElementUiModel<*>,
    modifier: Modifier = Modifier.fillMaxSize()
) {
    val backgroundColor = remember {
        if (color == Color.Unspecified) colors.shuffled().random() else color ?: Color.Unspecified
    }

    Box(
        modifier = Modifier
            .then(elementUiModel.modifier)
            .clip(RoundedCornerShape(5))
            .then(if (color == null) Modifier else Modifier.background(backgroundColor))
            .then(modifier)
            .padding(24.dp)
    ) {
        Text(
            text = elementUiModel.element.interactionTarget.toString(),
            fontSize = 21.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
}
