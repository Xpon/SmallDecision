package com.hj.smalldecision.inject

import android.app.Application
import com.hj.smalldecision.SmallDecisionApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityModule::class,
        ViewModelModule::class,
        DatabaseModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(smallDecisionApp: SmallDecisionApp)

}