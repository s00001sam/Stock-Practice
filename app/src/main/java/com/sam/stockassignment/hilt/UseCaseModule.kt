package com.sam.stockassignment.hilt

import com.sam.stockassignment.repo.Repository
import com.sam.stockassignment.repo.usecase.GetStocks
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
class UseCaseModule {

    @Provides
    fun provideGetStocks(repository: Repository) = GetStocks(repository)
}
