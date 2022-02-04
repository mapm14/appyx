package com.bumble.appyx.v2.core.children

import com.bumble.appyx.v2.core.node.Node
import com.bumble.appyx.v2.core.plugin.NodeAware
import kotlin.reflect.KClass

interface ChildAware<N: Node> : NodeAware<N> {

    fun <T : Node> whenChildAttached(
        child: KClass<T>,
        callback: ChildCallback<T>,
    )

    fun <T1 : Node, T2 : Node> whenChildrenAttached(
        child1: KClass<T1>,
        child2: KClass<T2>,
        callback: ChildrenCallback<T1, T2>,
    )

}