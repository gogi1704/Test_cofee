package com.l_george.test_cofee.di

import android.content.Context
import com.l_george.test_cofee.auth.AppAuth
import com.l_george.test_cofee.data.repository.AuthRepository
import com.l_george.test_cofee.data.repository.LocationsRepository
import com.l_george.test_cofee.data.repository.MenuRepository
import com.l_george.test_cofee.ui.viewModels.authViewModel.AuthViewModelFactory
import com.l_george.test_cofee.ui.viewModels.locationsViewModel.LocationViewModelFactory
import com.l_george.test_cofee.ui.viewModels.menuViewModel.MenuViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideAuthViewModelFactory(authRepository: AuthRepository): AuthViewModelFactory =
        AuthViewModelFactory(authRepository)

    @Provides
    @Singleton
    fun provideLocationViewModelFactory(locationRepository: LocationsRepository): LocationViewModelFactory =
        LocationViewModelFactory(locationRepository)

    @Provides
    @Singleton
    fun provideMenuViewModelFactory(menuRepository: MenuRepository): MenuViewModelFactory =
        MenuViewModelFactory(menuRepository)

    @Provides
    @Singleton
    fun provideAppAuth(): AppAuth = AppAuth(context)
}