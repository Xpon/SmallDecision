package com.hj.smalldecision.inject

import com.hj.smalldecision.MainActivity
import com.hj.smalldecision.ui.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributeMain(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun contributeHomeFragment(): HomeFragment

}