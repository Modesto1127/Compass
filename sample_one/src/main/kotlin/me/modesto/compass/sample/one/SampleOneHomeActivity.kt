package me.modesto.compass.sample.one

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import me.modesto.compass.anno.DestAnno
import me.modesto.compass.anno.ModuleAnno

/**
 * Description.
 *
 * @author Created by Luqian Ma in 2022/5/18
 */
@ModuleAnno("SampleOne")
@DestAnno(dest = "sample_one_home")
class SampleOneHomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Text("sample_one_home")
            }
        }
    }

}