package com.l_george.test_cofee.di

import com.l_george.test_cofee.data.repository.AuthRepository
import com.l_george.test_cofee.ui.viewModels.authViewModel.AuthViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideAuthViewModelFactory(authRepository: AuthRepository):AuthViewModelFactory = AuthViewModelFactory(authRepository)
}