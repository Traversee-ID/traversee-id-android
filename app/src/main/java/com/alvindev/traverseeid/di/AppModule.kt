package com.alvindev.traverseeid.di

import android.app.Application
import androidx.room.Room
import com.alvindev.moneysaver.feature_auth.data.FirebaseAuthRepository
import com.alvindev.traverseeid.core.domain.repository.BaseAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module

@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideAuthenticator(): BaseAuthRepository {
        return FirebaseAuthRepository()
    }

//    @Provides
//    @Singleton
//    fun provideDatabase(application: Application): TraverseeDatabase {
//        return Room.databaseBuilder(
//            application,
//            TraverseeDatabase::class.java,
//            TraverseeDatabase.DATABASE_NAME
//        )
//            .fallbackToDestructiveMigration()
//            .build()
//    }
}