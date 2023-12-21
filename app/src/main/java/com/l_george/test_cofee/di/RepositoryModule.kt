package com.l_george.test_cofee.di

import com.l_george.test_cofee.api.ApiService
import com.l_george.test_cofee.data.repository.AuthRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideAuthRepository(apiService: ApiService):AuthRepository = AuthRepository(apiService)
}