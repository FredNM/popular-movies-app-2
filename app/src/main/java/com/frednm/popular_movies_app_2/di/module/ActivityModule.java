package com.frednm.popular_movies_app_2.di.module;

import com.frednm.popular_movies_app_2.common.base.BaseActivity;
import com.frednm.popular_movies_app_2.features.detail.DetailActivity;
import com.frednm.popular_movies_app_2.features.home.HomeActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract HomeActivity homeActivity();

    @ContributesAndroidInjector
    abstract DetailActivity detailActivity();

    @ContributesAndroidInjector
    abstract BaseActivity baseActivity();
}
