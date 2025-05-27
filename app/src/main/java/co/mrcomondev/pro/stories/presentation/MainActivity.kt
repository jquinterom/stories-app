package co.mrcomondev.pro.stories.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import co.mrcomondev.pro.stories.presentation.composables.EnhancedSwipeBetweenBoxes
import co.mrcomondev.pro.stories.presentation.theme.StoriesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  @RequiresApi(Build.VERSION_CODES.TIRAMISU)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      StoriesTheme {
        Scaffold(modifier = Modifier.Companion.fillMaxSize()) { innerPadding ->
          EnhancedSwipeBetweenBoxes(modifier = Modifier.Companion.padding(innerPadding))
        }
      }
    }
  }
}