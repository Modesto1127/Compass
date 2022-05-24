package me.modesto.compass.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import me.modesto.compass.anno.DestAnno

/**
 * Description.
 *
 * @author Created by Luqian Ma in 2022/5/20
 */
@DestAnno("test_three")
class TestThreeActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Text(text = "test_three")
        }
    }

}