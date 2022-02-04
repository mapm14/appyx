package com.bumble.appyx.v2.core.routing

import android.os.Parcelable
import java.util.*
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
class RoutingKey<Routing> private constructor(
    val routing: @RawValue Routing,
    val id: String
) : Parcelable {

    constructor(routing: @RawValue Routing) : this(
        routing = routing,
        id = UUID.randomUUID().toString()
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RoutingKey<*>

        if (routing != other.routing) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = routing?.hashCode() ?: 0
        result = 31 * result + id.hashCode()
        return result
    }

}