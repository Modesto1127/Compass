package me.modesto.compass

import android.content.ComponentName
import android.content.Context
import android.content.Intent

data class DestStruct(val meta: DestMeta) {

    private lateinit var intent: Intent
    private val extra: MutableMap<String, Any> by lazy { mutableMapOf() }

    fun with(key: String, value: Any): DestStruct {
        extra[key] = value
        return this
    }

    fun go(context: Context) {
        val componentName = ComponentName(context, meta.dest)
        intent = Intent()
        intent.component = componentName
        if (!this::intent.isInitialized) {
            throw RuntimeException("Error ")
        }
        if (extra.isNotEmpty()) {
            extra.forEach { (key, value) ->
            }
        }
        context.startActivity(intent)
    }

}
