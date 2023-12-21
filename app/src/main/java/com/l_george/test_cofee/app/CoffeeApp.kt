package com.l_george.test_cofee.app

import android.app.Application
import com.l_george.test_cofee.di.AppComponent
import com.l_george.test_cofee.di.AppModule
import com.l_george.test_cofee.di.DaggerAppComponent


class CoffeeApp : Application() {
     lateinit var component: AppComponent
    override fun onCreate() {
        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
        super.onCreate()
    }
}