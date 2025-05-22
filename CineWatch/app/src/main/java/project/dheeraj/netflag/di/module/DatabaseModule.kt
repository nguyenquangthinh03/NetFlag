package project.dheeraj.netflag.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import project.dheeraj.netflag.data.local.BookmarkDatabase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application) = BookmarkDatabase.getInstance(application)

    @Provides
    @Singleton
    fun provideBookmarkDao(database: BookmarkDatabase) = database.getBookmarkDao()

}