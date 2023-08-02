package com.gamil.moahear.digibazaar

import android.app.Application
import com.gamil.moahear.digibazaar.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DigiBazaarApp: Application() {
    override fun onCreate() {
        super.onCreate()
        //Koin
        startKoin {
            androidContext(this@DigiBazaarApp)
            androidLogger()
            modules(appModule)
        }
    }
}