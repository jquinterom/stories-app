package co.mrcomondev.pro.stories.dataaccess

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by gesoft
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "stories_preferences")

class StoriesDataStore(private val dataStore: DataStore<Preferences>) {

  companion object {
    val STORY_KEY = stringSetPreferencesKey("story_key")
  }

  suspend fun updateStories(storyUrls: Set<String>) {
    dataStore.edit { preferences ->
      preferences[STORY_KEY] = storyUrls

    }
  }

  val stories: Flow<Set<String>> = dataStore.data.map { preferences ->
    preferences[STORY_KEY] ?: emptySet()
  }

}