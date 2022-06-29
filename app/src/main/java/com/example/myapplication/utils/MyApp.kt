package com.example.myapplication.utils

import android.app.Application
import com.example.myapplication.di.DependencyStorage
import com.example.myapplication.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        DependencyStorage.init(this)

        startKoin {
            androidContext(this@MyApp)
            modules(appModules)
        }
    }
}

