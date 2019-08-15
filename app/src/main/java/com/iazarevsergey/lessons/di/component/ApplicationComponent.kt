package com.iazarevsergey.lessons.di.component

import android.app.Application
import com.iazarevsergey.lessons.App
import com.iazarevsergey.lessons.di.module.*
import com.iazarevsergey.lessons.di.module.ViewModelModule
import com.iazarevsergey.lessons.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityModule::class,
    ViewModelModule::class,
    NetworkModule::class,
    RepositoryModule::class,
    DbModule::class,
    AndroidModule::class,
    DatasourceModule::class
])
interface ApplicationComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(application: App)
}