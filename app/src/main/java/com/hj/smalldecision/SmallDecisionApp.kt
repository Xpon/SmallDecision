package com.hj.smalldecision

import android.app.Application
import com.hj.goodweight.inject.AppInjector
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

class SmallDecisionApp : Application(), HasAndroidInjector {

    companion object {
        lateinit var instance: SmallDecisionApp
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
//            Stetho.initializeWithDefaults(this)
        }
        AppInjector.init(this)
        AndroidThreeTen.init(this)
    }
    override fun androidInjector() = dispatchingAndroidInjector
}