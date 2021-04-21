package com.hj.smalldecision.extension

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

@Suppress("DEPRECATION")
fun Context.vibrate(pattern: LongArray) {
    if (Build.VERSION.SDK_INT >= 26) {
        (getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).vibrate(VibrationEffect.createWaveform(pattern, -1))
    } else {
        (getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).vibrate(pattern, -1)
    }
}

fun Context.dpToPx(dp: Float): Int {
    return if (dp == 0f) {
        0
    } else {
        val scale = resources.displayMetrics.density
        (dp * scale + 0.5f).toInt()
    }
}
