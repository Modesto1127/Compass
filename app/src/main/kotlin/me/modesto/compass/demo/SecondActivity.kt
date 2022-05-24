package me.modesto.compass.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import me.modesto.compass.anno.DestAnno
import me.modesto.compass.demo.ui.theme.CompassTheme

/**
 * Description.
 *
 * @author Created by Luqian Ma in 2022/5/18
 */
@DestAnno(path = "second")
class SecondActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompassTheme {
                Text(text = "SecondActivity")
            }
        }
    }

}