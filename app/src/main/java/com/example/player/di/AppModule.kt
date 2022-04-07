package com.example.player.di

import android.content.Context
import androidx.room.Room
import com.example.player.data.MusicDao
import com.example.player.data.MusicDatabase
import com.example.player.repository.MainRepository
import com.example.player.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun provideMusicDao(appDatabase: MusicDatabase): MusicDao {
        return appDatabase.musicDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): MusicDatabase {
        return Room.databaseBuilder(
            appContext,
            MusicDatabase::class.java,
            MusicDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideRepositporyImpp(repositoryImpl: RepositoryImpl): MainRepository {
        return repositoryImpl
    }

}