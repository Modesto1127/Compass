package me.modesto.compass

import android.content.Context

/**
 * Description.
 *
 * @author Created by Luqian Ma in 2022/5/31
 */
fun compass(dest: String, context: Context, priority: Int = 0, invoke: (DestRequest.() -> Unit)? = null) {
    Compass.dest(dest, priority).run {
        invoke?.invoke(this)
        go(context)
    }
}

fun DestRequest.params(builderAction: MutableMap<String, Any>.() -> Unit) {
    buildMap(builderAction).forEach { (key, value) ->
        with(key, value)
    }
}