package com.l_george.test_cofee.di

import com.l_george.test_cofee.ui.fragments.AuthFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class , RepositoryModule::class , ApiModule::class])
@Singleton
interface AppComponent {
   fun injectAuthFragment(authFragment: AuthFragment)
}