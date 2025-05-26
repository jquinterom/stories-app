package co.mrcomondev.pro.stories.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.mrcomondev.pro.stories.dataaccess.StoriesDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by gesoft
 */
@HiltViewModel
class StoriesViewModel @Inject constructor(
  private val storiesRepository: StoriesDataStore
) : ViewModel() {

  val stories = storiesRepository.stories

  fun addUrlStory(url: String) {
    viewModelScope.launch {
      val current = stories.first()
      storiesRepository.updateStories(current + url)
    }
  }

  private fun deleteUrlStory(url: String) {
    viewModelScope.launch {
      val current = stories.first()
      storiesRepository.updateStories(current - url)
    }
  }

  private fun storyExists(url: String): Flow<Boolean> {
    return stories.map { urls ->
      url in urls
    }
  }
}