package co.mrcomondev.pro.stories.presentation.ui.composables


import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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

  val pagerState = rememberPagerState(pageCount = { colors.size })

  val scope = rememberCoroutineScope()

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
        val isSelected = pagerState.currentPage == index
        val size by animateDpAsState(
          targetValue = if (isSelected) 32.dp else 28.dp
        )

        Box(
          modifier = Modifier
            .size(size)
            .border(
              1.dp,
              if (index == pagerState.currentPage) Color.White else Color.White.copy(alpha = 0.5f),
              CircleShape
            )
            .size(30.dp)
            .clickable(
              interactionSource = remember { MutableInteractionSource() },
              indication = ripple(
                bounded = false,
                radius = 24.dp,
                color = MaterialTheme.colorScheme.primary
              )
            ) {
              scope.launch {
                pagerState.animateScrollToPage(index)
              }
            }
            .background(
              Color.White.copy(alpha = 0.5f),
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
            text = "Pantalla ${page + 1}",
            style = MaterialTheme.typography.displayLarge,
            color = Color.White
          )
        }
      }
    }


  }
}