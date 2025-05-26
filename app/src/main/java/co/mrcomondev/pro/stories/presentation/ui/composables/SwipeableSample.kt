package co.mrcomondev.pro.stories.presentation.ui.composables


import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EnhancedSwipeBetweenBoxes(modifier: Modifier) {

  val colors = listOf(
    Color.Red,
    Color.Blue,
    Color.Green,
    Color.Yellow
  )

  val borderWidth: Dp = 2.dp

  var remainingTime by rememberSaveable { mutableIntStateOf(3) }
  var progress by rememberSaveable { mutableFloatStateOf(.3f) }

  val pagerState = rememberPagerState(pageCount = { colors.size })

  val activeColor: Color = MaterialTheme.colorScheme.primary

  val scope = rememberCoroutineScope()


  LaunchedEffect(pagerState.currentPage) {
    if (pagerState.currentPage < pagerState.pageCount) {
      while (remainingTime > 0) {
        delay(1000L)
        remainingTime--
        progress = progress + .35f
      }

      scope.launch {
        pagerState.animateScrollToPage(pagerState.currentPage + 1)
      }

      remainingTime = 3
      progress = .3f

      if(pagerState.currentPage == pagerState.pageCount - 1) progress = 0f
    }
  }

  Column(modifier = modifier.fillMaxSize()) {
    Row(
      modifier = Modifier
        .background(Color.Black)
        .fillMaxWidth()
        .padding(4.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(32.dp)
          .background(
            Color.White.copy(alpha = 0.2f),
            shape = CircleShape,
          ),
      ) {
        Icon(
          imageVector = Icons.Default.Add,
          contentDescription = "Add",
          modifier = Modifier.align(Alignment.Center)
        )
      }

      repeat(colors.size) { index ->
        val isPageActive = pagerState.currentPage == index

        val size by animateDpAsState(
          targetValue = if (isPageActive) 32.dp else 28.dp
        )

        Box(
          modifier = Modifier
            .size(size)
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

              if (progress > 0 && isPageActive) {
                drawArc(
                  color = activeColor,
                  startAngle = -90f,
                  sweepAngle = 360f * progress,
                  useCenter = false,
                  size = Size(
                    width = size.toPx(),
                    height = size.toPx()
                  ),
                  style = Stroke(width = borderWidth.toPx(), cap = StrokeCap.Round)
                )
              }
            }
            .background(
              color = colors[index],
              shape = CircleShape,
            )
        )
      }
    }

    Box(
      modifier = Modifier
    ) {
      HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
      ) { page ->
        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(colors[page]),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "Screen ${page + 1}",
            style = MaterialTheme.typography.displayLarge,
            color = Color.White
          )
        }
      }
    }
  }
}
