package com.l_george.test_cofee.di

import com.l_george.test_cofee.ui.fragments.AuthFragment
import com.l_george.test_cofee.ui.fragments.CoffeeListFragment
import com.l_george.test_cofee.ui.fragments.MainActivity
import com.l_george.test_cofee.ui.fragments.MapFragment
import com.l_george.test_cofee.ui.fragments.MenuFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class , RepositoryModule::class , ApiModule::class])
@Singleton
interface AppComponent {
   fun inject(authFragment: AuthFragment)
   fun inject(activity: MainActivity)
   fun inject(coffeeFragment: CoffeeListFragment)
   fun inject(coffeeFragment: MapFragment)
   fun inject(menuFragment: MenuFragment)
}