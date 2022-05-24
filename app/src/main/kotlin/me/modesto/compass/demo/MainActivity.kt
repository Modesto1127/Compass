package me.modesto.compass.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import me.modesto.compass.Compass
import me.modesto.compass.anno.DestAnno
import me.modesto.compass.demo.ui.theme.CompassTheme

@DestAnno(path = "main")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompassTheme {
                Column {
                    Text(text = "Hello Compass")
                    Button(onClick = { Compass.dest("second").go(this@MainActivity) }) {
                        Text(text = "go second activity")
                    }
                    Button(onClick = { Compass.dest("test_one").go(this@MainActivity) }) {
                        Text(text = "go test one activity")
                    }
                    Button(onClick = { Compass.dest("test_two").go(this@MainActivity) }) {
                        Text(text = "go test two activity")
                    }
                    Button(onClick = { Compass.dest("test_three").go(this@MainActivity) }) {
                        Text(text = "go test three activity")
                    }
                }
            }
        }
    }
}