package com.iazarevsergey.lessons.di.component

import android.app.Application
import com.iazarevsergey.lessons.di.WeatherApplication
import com.iazarevsergey.lessons.di.module.ActivityModule
import com.iazarevsergey.lessons.di.module.DataModule
import com.iazarevsergey.lessons.di.module.FragmentModule
import com.iazarevsergey.lessons.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelModule::class, DataModule::class, AndroidSupportInjectionModule::class, ActivityModule::class, FragmentModule::class])
interface ApplicationComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun dataModule(dataModule: DataModule): Builder

        fun build(): ApplicationComponent
    }

    fun inject(application: WeatherApplication)
}