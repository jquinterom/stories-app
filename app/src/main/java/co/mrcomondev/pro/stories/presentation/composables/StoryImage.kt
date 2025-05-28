package co.mrcomondev.pro.stories.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.ImageRequest

/**
 * Created by gesoft
 */
@Composable
fun StoryImage(pagerState: PagerState, image: String) {
  val context = LocalContext.current

  val imageLoader= LocalContext.current.imageLoader

  Box(
    modifier = Modifier
  ) {
    HorizontalPager(
      state = pagerState,
      modifier = Modifier.fillMaxSize()
    ) { page ->

      val imageRequest = remember(image) {
        ImageRequest.Builder(context)
          .data(image)
          .crossfade(true)
          .build()
      }

      AsyncImage(
        modifier = Modifier.fillMaxSize(),
        model = imageRequest,
        contentDescription = "story",
        contentScale = ContentScale.Crop,
        imageLoader = imageLoader,
      )
    }
  }
}