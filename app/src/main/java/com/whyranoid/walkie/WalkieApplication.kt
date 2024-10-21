package com.whyranoid.walkie

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.net.SocketTimeoutException



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
                    useCaseModule,
                    databaseModule,
                    networkModule,
                ),
            )
        }

        super.onCreate()

        // 기본 예외 핸들러 설정
        Thread.setDefaultUncaughtExceptionHandler { _, e ->
            if (e is SocketTimeoutException) {
                System.err.println("SocketTimeoutException 발생: " + e.message)
                // todo dialog 띄워주기
            }
        }
    }
}
