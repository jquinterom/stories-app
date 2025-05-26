package co.mrcomondev.pro.stories.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import co.mrcomondev.pro.stories.dataaccess.StoriesDataStore
import co.mrcomondev.pro.stories.dataaccess.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by gesoft
 */
@InstallIn(SingletonComponent::class)
@Module
object StoryModule {

  @Provides
  @Singleton
  fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
    return context.dataStore
  }


  @Provides
  @Singleton
  fun provideStoriesDataStore(dataStore: DataStore<Preferences>): StoriesDataStore {
    return StoriesDataStore(dataStore)
  }

}