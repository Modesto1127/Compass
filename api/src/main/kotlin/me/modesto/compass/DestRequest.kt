package me.modesto.compass

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher

/**
 * Description.
 *
 * @author Created by Luqian Ma in 2022/5/30
 */
open class DestRequest(private val meta: DestMeta) {

    private val intent by lazy { Intent() }
    private var requested = false
    private lateinit var launcher: ActivityResultLauncher<Intent>

    fun with(key: String, value: Any): DestRequest {
        intent.addExtra(key, value)
        return this
    }

    fun resultLauncher(launcher: ActivityResultLauncher<Intent>): DestRequest {
        this.launcher = launcher
        return this
    }

    open fun go(context: Context) {
        if (!requested) {
            requested = true
            intent.component = ComponentName(context, meta.dest)
            if (this::launcher.isInitialized) {
                launcher.launch(intent)
            } else {
                context.startActivity(intent)
            }
        }
    }

}

internal class WrongDestRequest(private val dest: String) : DestRequest(DestMeta(dest)) {
    override fun go(context: Context) {
        Toast.makeText(context, "You passed in the wrong destination: $dest", Toast.LENGTH_LONG).show()
    }
}