package me.modesto.compass.demo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import me.modesto.compass.Compass
import me.modesto.compass.anno.DestAnno
import me.modesto.compass.compass
import me.modesto.compass.demo.ui.theme.CompassTheme
import me.modesto.compass.params

@DestAnno(dest = "main")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                RESULT_OK -> {
                    val msg = result.data?.getStringExtra("msg") ?: "No value"
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                }
                else      -> {
                    Toast.makeText(this, "返回数据失败", Toast.LENGTH_SHORT).show()
                }
            }
        }
        setContent {
            val testcase = Testcase("testcase", false, "Testcase")
            CompassTheme {
                Compass.dest("test").with("test", 1).resultLauncher(activityResultLauncher)
                Column {
                    Text(text = "Hello Compass")
                    Button(onClick = { Compass.dest("second").with("testcase", testcase).go(this@MainActivity) }) {
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
                    Button(onClick = { Compass.dest("sample_one_home").go(this@MainActivity) }) {
                        Text(text = "go sample one home activity")
                    }
                    Button(onClick = { Compass.dest("test").go(this@MainActivity) }) {
                        Text(text = "go second activity by api-ktx")
                    }
                    Button(onClick = {
                        compass("second", this@MainActivity) {
                            resultLauncher(activityResultLauncher)
                        }
                    }) {
                        Text(text = "go activity need result")
                    }
                    Button(onClick = {
                        compass("second", this@MainActivity) {
                            params {
                                put("key_one", 1)
                                put("key_two", "String")
                                put("testcase", testcase)
                            }
                        }
                    }) {
                        Text(text = "go second activity by dsl")
                    }
                }
            }
        }
    }
}