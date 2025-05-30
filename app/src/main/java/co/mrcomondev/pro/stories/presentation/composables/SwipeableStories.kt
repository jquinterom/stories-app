package co.mrcomondev.pro.stories.presentation.composables

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import co.mrcomondev.pro.stories.presentation.viewmodel.StoriesViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EnhancedSwipeBetweenBoxes(
  modifier: Modifier,
  storiesViewModel: StoriesViewModel = hiltViewModel()
) {
  val stories = storiesViewModel.stories.collectAsState(initial = emptySet())

  var remainingTime by rememberSaveable { mutableIntStateOf(3) }
  var progress by rememberSaveable { mutableFloatStateOf(.0f) }

  val pagerState = rememberPagerState(pageCount = { stories.value.size })


  val scope = rememberCoroutineScope()
  var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

  val openAlertDialog = remember { mutableStateOf(false) }

  val getImage: (Int) -> String = { pageIndex ->
    if (stories.value.isNotEmpty()) {
      stories.value.elementAt(index = pageIndex)
    } else {
      ""
    }
  }

  val currentImage: String = getImage(pagerState.currentPage)

  LaunchedEffect(pagerState.currentPage, stories.value) {
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

      if (pagerState.currentPage == pagerState.pageCount - 1) progress = 0f
    }
  }

  if (openAlertDialog.value) {
    AlertDialogExample(
      onDismissRequest = { openAlertDialog.value = false },
      onConfirmation = {
        selectedImageUri.let { uri ->
          storiesViewModel.addUrlStory(uri.toString())
        }

        selectedImageUri = null
        openAlertDialog.value = false
      },
      "Save Image",
      "Are you sure to upload this image?",
      icon = Icons.Default.Info
    )
  }

  LaunchedEffect(selectedImageUri) {
    if (selectedImageUri != null) {
      openAlertDialog.value = true
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

      ImagePicker(
        modifier = Modifier.size(32.dp),
        onImageSelected = { uri ->
          selectedImageUri = uri
        }
      )

      repeat(stories.value.size) { index ->
        val isPageActive = pagerState.currentPage == index

        val size by animateDpAsState(
          targetValue = if (isPageActive) 32.dp else 28.dp
        )

        ImagePreview(
          model = getImage(index),
          size = size,
          index = index,
          pagerState = pagerState,
          scope = scope,
        )
      }
    }

    if (stories.value.isNotEmpty() && pagerState.currentPage < pagerState.pageCount) {
      LoaderIndicator(progress = progress)
    }

    StoryImage(
      pagerState = pagerState,
      image = currentImage
    )
  }
}
