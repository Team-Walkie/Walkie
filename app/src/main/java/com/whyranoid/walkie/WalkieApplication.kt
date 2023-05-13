package com.whyranoid.walkie

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WalkieApplication : Application() {
    override fun onCreate() {

        startKoin {
            androidLogger()
            androidContext(this@WalkieApplication)
            modules(
                listOf(
                    viewModelModule,
                    repositoryModule,
                    dataSourceModule,
                    useCaseModule
                )
            )
        }

        super.onCreate()
    }
}