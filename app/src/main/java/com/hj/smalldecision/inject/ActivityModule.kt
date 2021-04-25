package com.hj.smalldecision.inject

import com.hj.smalldecision.MainActivity
import com.hj.smalldecision.ui.settings.SettingsActivity
import com.hj.smalldecision.ui.home.HomeFragment
import com.hj.smalldecision.ui.home.ModuleEditActivity
import com.hj.smalldecision.ui.settings.PrivacyActivity
import com.hj.smalldecision.ui.settings.UserTreatyActivity
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
    internal abstract fun contributeSettingsActivity(): SettingsActivity

    @ContributesAndroidInjector
    internal abstract fun contributeUserTreatyActivity(): UserTreatyActivity

    @ContributesAndroidInjector
    internal abstract fun contributePrivacyActivity(): PrivacyActivity
}