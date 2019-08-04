package com.iazarevsergey.lessons.di.module

import com.iazarevsergey.lessons.ui.DetailWeatherFragment
import com.iazarevsergey.lessons.ui.ListWeathersFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeListWeathersFragment(): ListWeathersFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailWeatherFragment(): DetailWeatherFragment

}