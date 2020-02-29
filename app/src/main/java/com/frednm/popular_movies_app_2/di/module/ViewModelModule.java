package com.frednm.popular_movies_app_2.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.frednm.popular_movies_app_2.common.factory.FactoryViewModel;
import com.frednm.popular_movies_app_2.di.key.ViewModelKey;
import com.frednm.popular_movies_app_2.features.detail.DetailViewModel;
import com.frednm.popular_movies_app_2.features.home.HomeViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    abstract ViewModel bindHomeViewModel(HomeViewModel homeViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel.class)
    abstract ViewModel bindDetailViewModel(DetailViewModel homeViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(FactoryViewModel factory);
}
