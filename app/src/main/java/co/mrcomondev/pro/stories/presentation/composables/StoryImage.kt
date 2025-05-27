package co.mrcomondev.pro.stories.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.imageLoader

/**
 * Created by gesoft
 */
@Composable
fun StoryImage(pagerState: PagerState, image: String) {
  Box(
    modifier = Modifier
  ) {
    HorizontalPager(
      state = pagerState,
      modifier = Modifier.fillMaxSize()
    ) { page ->
      AsyncImage(
        modifier = Modifier.fillMaxSize(),
        model = image,
        contentDescription = "story",
        contentScale = ContentScale.Crop,
        imageLoader = LocalContext.current.imageLoader,
      )
    }
  }
}