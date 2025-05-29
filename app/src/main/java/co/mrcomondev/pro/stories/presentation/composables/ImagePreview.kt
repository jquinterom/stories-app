package co.mrcomondev.pro.stories.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import co.mrcomondev.pro.stories.R
import coil.compose.AsyncImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Created by gesoft
 */
@Composable
fun ImagePreview(
  model: String,
  size: Dp,
  index: Int,
  pagerState: PagerState,
  scope: CoroutineScope,
  borderWidth: Dp,
  firstTime: Boolean = true
) {

  AsyncImage(
    model = model,
    contentDescription = "image",
    modifier = Modifier
      .size(size)
      .clip(CircleShape)
      .clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = ripple(
          bounded = false,
          color = MaterialTheme.colorScheme.primary
        )
      ) {
        scope.launch {
          pagerState.animateScrollToPage(index)
        }
      }
      .drawWithContent {
        drawContent()

        drawArc(
          color = if (firstTime) Color.White else Color.Black,
          startAngle = -90f,
          sweepAngle = 360f,
          useCenter = false,
          size = Size(
            width = size.toPx(),
            height = size.toPx()
          ),
          style = Stroke(width = borderWidth.toPx(), cap = StrokeCap.Round)
        )
      },
    contentScale = ContentScale.Crop,
    error = painterResource(R.drawable.ic_launcher_background),
  )
}