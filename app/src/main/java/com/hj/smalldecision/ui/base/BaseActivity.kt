package com.hj.smalldecision.ui.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hj.smalldecision.R
import com.hj.smalldecision.utils.ColorUtils
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

open class BaseActivity : AppCompatActivity(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector() = dispatchingAndroidInjector
    override fun onCreate(savedInstanceState: Bundle?) {
        ColorUtils.setStatusBarColor(this, R.color.white)
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT; // 禁用横屏
    }
}