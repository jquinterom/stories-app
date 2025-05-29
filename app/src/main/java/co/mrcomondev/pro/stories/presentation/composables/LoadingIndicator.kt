package co.mrcomondev.pro.stories.presentation.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Created by gesoft
 */
@Composable
fun LoaderIndicator(progress: Float) {
  LinearProgressIndicator(
    progress = { progress },
    modifier = Modifier
      .fillMaxWidth()
      .height(2.dp),
    color = MaterialTheme.colorScheme.primary,
    trackColor = MaterialTheme.colorScheme.surfaceVariant,
  )
}