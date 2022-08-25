package me.modesto.compass

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

/**
 * Description.
 *
 * @author Created by Luqian Ma in 2022/5/30
 */
internal fun Intent.addExtra(key: String, value: Any): Intent {
    when (value) {
        is Byte         -> putExtra(key, value)
        is Short        -> putExtra(key, value)
        is Int          -> putExtra(key, value)
        is Long         -> putExtra(key, value)
        is Float        -> putExtra(key, value)
        is Double       -> putExtra(key, value)
        is Char         -> putExtra(key, value)
        is String       -> putExtra(key, value)
        is CharSequence -> putExtra(key, value)
        is Boolean      -> putExtra(key, value)
        is ByteArray    -> putExtra(key, value)
        is ShortArray   -> putExtra(key, value)
        is IntArray     -> putExtra(key, value)
        is LongArray    -> putExtra(key, value)
        is FloatArray   -> putExtra(key, value)
        is DoubleArray  -> putExtra(key, value)
        is CharArray    -> putExtra(key, value)
        is BooleanArray -> putExtra(key, value)
        is Array<*>     -> {
            when {
                value.isArrayOf<String>()       -> putExtra(key, value as Array<String?>)
                value.isArrayOf<Parcelable>()   -> putExtra(key, value as Array<Parcelable?>)
                value.isArrayOf<CharSequence>() -> putExtra(key, value as Array<CharSequence?>)
                else                            -> putExtra(key, value)
            }
        }
        is Bundle       -> putExtra(key, value)
        is Parcelable   -> putExtra(key, value)
        is Serializable -> putExtra(key, value)
    }
    return this
}