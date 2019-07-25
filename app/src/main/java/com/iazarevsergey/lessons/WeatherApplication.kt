package com.iazarevsergey.lessons

import android.app.Activity
import android.app.Application
import com.iazarevsergey.lessons.di.component.DaggerApplicationComponent
import com.iazarevsergey.lessons.di.module.AndroidModule
import com.iazarevsergey.lessons.di.module.DataModule
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class WeatherApplication:Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.builder()
            .application(this)
            .dataModule(DataModule())
            .build()
            .inject(this)
    }
}