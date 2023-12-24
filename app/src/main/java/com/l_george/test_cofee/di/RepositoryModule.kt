package com.l_george.test_cofee.di

import com.l_george.test_cofee.auth.AppAuth
import com.l_george.test_cofee.api.ApiService
import com.l_george.test_cofee.data.repository.AuthRepository
import com.l_george.test_cofee.data.repository.LocationsRepository
import com.l_george.test_cofee.data.repository.MenuRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideAuthRepository(apiService: ApiService, auth: AppAuth): AuthRepository =
        AuthRepository(apiService, auth)

    @Provides
    @Singleton
    fun provideLocationsRepository(apiService: ApiService): LocationsRepository =
        LocationsRepository(apiService)

    @Provides
    @Singleton
    fun provideMenuRepository(apiService: ApiService): MenuRepository =
        MenuRepository(apiService)
}