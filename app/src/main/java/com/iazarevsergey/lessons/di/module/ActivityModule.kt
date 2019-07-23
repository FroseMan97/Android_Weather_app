package com.iazarevsergey.lessons.di.module

import com.iazarevsergey.lessons.ui.WeatherActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeWeatherActivity(): WeatherActivity

}
