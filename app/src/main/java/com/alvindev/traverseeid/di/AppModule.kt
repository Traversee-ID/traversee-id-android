package com.alvindev.traverseeid.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.alvindev.traverseeid.core.data.remote.FirebaseAuthRepository
import com.alvindev.traverseeid.core.data.local.UserDataStoreRepositoryImpl
import com.alvindev.traverseeid.core.domain.repository.BaseAuthRepository
import com.alvindev.traverseeid.core.domain.repository.UserDataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

private const val USER_PREFERENCES = "user_preferences"
@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideAuthenticator(): BaseAuthRepository {
        return FirebaseAuthRepository()
    }

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(appContext,USER_PREFERENCES)),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(USER_PREFERENCES) }
        )
    }

    @Singleton
    @Provides
    fun provideUserDataStoreRepository(dataStore: DataStore<Preferences>): UserDataStoreRepository {
        return UserDataStoreRepositoryImpl(dataStore)
    }

    @Singleton
    @Provides
    fun provideAppContext(@ApplicationContext appContext: Context): Context {
        return appContext.applicationContext
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