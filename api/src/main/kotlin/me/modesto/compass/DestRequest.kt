package me.modesto.compass

import android.content.ComponentName
import android.content.Context
import android.content.Intent

/**
 * Description.
 *
 * @author Created by Luqian Ma in 2022/5/30
 */
class DestRequest(private val meta: DestMeta) {

    private lateinit var extras: MutableMap<String, Any>

    fun with(key: String, value: Any): DestRequest {
        if (!this::extras.isInitialized) extras = mutableMapOf()
        extras[key] = value
        return this
    }

    fun go(context: Context) {
        val intent = Intent()
        intent.component = ComponentName(context, meta.dest)
        if (this::extras.isInitialized) {
            intent.addExtras(extras)
        }
        context.startActivity(intent)
    }

}