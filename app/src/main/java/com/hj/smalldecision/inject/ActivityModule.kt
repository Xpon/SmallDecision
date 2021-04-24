package com.hj.smalldecision.inject

import com.hj.smalldecision.MainActivity
import com.hj.smalldecision.ui.dice.Activity_dice
import com.hj.smalldecision.ui.home.HomeFragment
import com.hj.smalldecision.ui.home.ModuleEditActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributeMain(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    internal abstract fun contributeModuleEditActivity(): ModuleEditActivity

    @ContributesAndroidInjector
    internal abstract fun contributeActivity_dice(): Activity_dice

}