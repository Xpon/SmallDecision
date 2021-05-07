package com.hj.smalldecision.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.hj.smalldecision.R

object ColorUtils {

    const val FEMALE = 0
    const val MALE = 1

    fun setStatusBarColor(activity: Activity, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //21版本以上
            val window = activity.window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = activity.resources.getColor(color)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //19 - 21之间
            val window: Window = activity.window
            window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            )
        }
    }

    fun getDefaultChooseColor(context: Context): Array<Int> {
        return arrayOf(
            context.resources.getColor(R.color.choose_game_default_color_1),
            context.resources.getColor(R.color.choose_game_default_color_2),
            context.resources.getColor(R.color.choose_game_default_color_3),
            context.resources.getColor(R.color.choose_game_default_color_4),
            context.resources.getColor(R.color.choose_game_default_color_5),
            context.resources.getColor(R.color.choose_game_default_color_6),
            context.resources.getColor(R.color.choose_game_default_color_7),
            context.resources.getColor(R.color.choose_game_default_color_8),
            context.resources.getColor(R.color.choose_game_default_color_9),
            context.resources.getColor(R.color.choose_game_default_color_10),
            context.resources.getColor(R.color.choose_game_default_color_11),
            context.resources.getColor(R.color.choose_game_default_color_12),
            context.resources.getColor(R.color.choose_game_default_color_13),
            context.resources.getColor(R.color.choose_game_default_color_14),
            context.resources.getColor(R.color.choose_game_default_color_15)
        )
    }
}