package com.sam.stockassignment.hilt

import com.sam.stockassignment.view.helper.SyncRecyclerViewHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewWithFragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.android.scopes.ViewScoped

@InstallIn(ActivityRetainedComponent::class)
@Module
class ViewModule {

    @Provides
    fun provideSyncRecyclerViewHelper() = SyncRecyclerViewHelper()
}
