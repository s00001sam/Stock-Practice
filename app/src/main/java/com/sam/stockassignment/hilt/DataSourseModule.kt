package com.sam.stockassignment.hilt

import android.content.Context
import com.sam.stockassignment.repo.api.Api
import com.sam.stockassignment.repo.datasource.DataSource
import com.sam.stockassignment.repo.datasource.LocalDataSource
import com.sam.stockassignment.repo.datasource.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataSourseModule {
    @Singleton
    @LocalData
    @Provides
    fun providerLocalDataSource(): DataSource {
        return LocalDataSource()
    }

    @Singleton
    @RemoteData
    @Provides
    fun providerRemoteDataSource(api: Api): DataSource {
        return RemoteDataSource(api)
    }
}