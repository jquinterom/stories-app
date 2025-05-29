package co.mrcomondev.pro.stories.presentation.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
  firstTime: Boolean = true
) {
  val containerSize = size + 8.dp
  val archPadding = 4.dp
  val archRadiusPadding = 6.dp

  Box(
    modifier = Modifier
      .size(containerSize)
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
      },
    contentAlignment = Alignment.Center
  ) {

    AsyncImage(
      model = model,
      contentDescription = "image",
      modifier = Modifier
        .size(size)
        .align(Alignment.Center)
        .clip(CircleShape)
        .background(color = Color.Red),
      contentScale = ContentScale.Crop,
      error = painterResource(R.drawable.ic_launcher_background),
    )

    Canvas(
      modifier = Modifier
        .fillMaxSize()
        .align(Alignment.Center)
    ) {
      val arcRadius = (size.toPx() + archRadiusPadding.toPx()) / 2
      val center = Offset(size.toPx() / 2 + archPadding.toPx(), size.toPx() / 2 + archPadding.toPx())
      val firstTimeColor = if(firstTime) Color.White else Color.Black

      drawCircle(
        brush = Brush.radialGradient(listOf(Color.Black, firstTimeColor)),
        radius = arcRadius,
        center = center,
        style = Stroke(
          width = 1.dp.toPx(),
        )
      )
    }
  }
}

@Preview
@Composable
private fun ImagePreviewPrev() {
  ImagePreview(
    model = "",
    size = 100.dp,
    index = 0,
    pagerState = rememberPagerState(pageCount = { 2 }),
    scope = rememberCoroutineScope(),
  )
}