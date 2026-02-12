package com.nasmedia.naptracker.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.nasmedia.naptracker.sample.ui.NapTrackerSampleApp
import com.nasmedia.naptracker.sample.ui.theme.NapTrackerSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NapTrackerSampleTheme {
                NapTrackerSampleApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}
