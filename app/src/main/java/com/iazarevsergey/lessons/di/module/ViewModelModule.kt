package com.iazarevsergey.lessons.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iazarevsergey.lessons.factory.ViewModelFactory
import com.iazarevsergey.lessons.viewmodel.DetailWeatherViewModel
import com.iazarevsergey.lessons.viewmodel.ListWeathersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ListWeathersViewModel::class)
    protected abstract fun listWeathersViewModel(listWeathersViewModel: ListWeathersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailWeatherViewModel::class)
    protected abstract fun detailWeatherViewModel(detailWeatherViewModel: DetailWeatherViewModel): ViewModel


}