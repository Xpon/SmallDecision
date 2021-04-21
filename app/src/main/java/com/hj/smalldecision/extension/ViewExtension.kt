package com.hj.smalldecision.extension

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.TextAppearanceSpan
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.core.view.drawToBitmap
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import timber.log.Timber

fun View.navigate(
    resId: Int,
    bundle: Bundle? = null,
    navOptions: NavOptions? = null
) {
    try {
        findNavController().navigate(resId, bundle, navOptions)
    } catch (e: IllegalArgumentException) {
        // Workaround with https://issuetracker.google.com/issues/128881182
    } catch (e: IllegalStateException) {
        Timber.w("View $this does not have a NavController set")
    }
}

fun View.navigateUp() {
    try {
        findNavController().navigateUp()
    } catch (e: IllegalArgumentException) {
        // Workaround with https://issuetracker.google.com/issues/128881182
    } catch (e: IllegalStateException) {
        Timber.w("View $this does not have a NavController set")
    }
}

fun TextView.highLight(
    target: String?,
    ignoreCase: Boolean = true,
    @ColorInt color: Int
) {
    if (target.isNullOrBlank()) {
        return
    }
    val text = this.text.toString()
    val spannable = SpannableString(text)
    var index = text.indexOf(target, ignoreCase = ignoreCase)
    while (index != -1) {
        spannable.setSpan(
            TextAppearanceSpan(null, 0, 0, ColorStateList.valueOf(color), null),
            index, index + target.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        index = text.indexOf(target, index + target.length, ignoreCase = ignoreCase)
    }
    setText(spannable)
}

internal fun Context.getColorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)

internal fun TextView.setTextColorRes(@ColorRes color: Int) = setTextColor(context.getColorCompat(color))

fun BottomNavigationView.show(): Boolean {
    if (visibility == View.VISIBLE) return true

    val parent = parent as ViewGroup
    // View needs to be laid out to create a snapshot & know position to animate. If view isn't
    // laid out yet, need to do this manually.
    if (!isLaidOut) {
        measure(
            View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.AT_MOST)
        )
        layout(parent.left, parent.height - measuredHeight, parent.right, parent.height)
    }
    if (width <= 0 || height <= 0) return false

    val drawable = BitmapDrawable(context.resources, drawToBitmap())
    drawable.setBounds(left, parent.height, right, parent.height + height)
    parent.overlay.add(drawable)
    ValueAnimator.ofInt(parent.height, top).apply {
        startDelay = 100L
        duration = 300L
        interpolator = AnimationUtils.loadInterpolator(
            context,
            android.R.interpolator.linear_out_slow_in
        )
        addUpdateListener {
            val newTop = it.animatedValue as Int
            drawable.setBounds(left, newTop, right, newTop + height)
        }
        doOnEnd {
            parent.overlay.remove(drawable)
            visibility = View.VISIBLE
        }
        start()
    }
    return true
}

fun BottomNavigationView.hide() {
    if (visibility == View.GONE || !isLaidOut) return

    val drawable = BitmapDrawable(context.resources, drawToBitmap())
    val parent = parent as ViewGroup
    drawable.setBounds(left, top, right, bottom)
    parent.overlay.add(drawable)
    visibility = View.GONE
    ValueAnimator.ofInt(top, parent.height).apply {
        startDelay = 100L
        duration = 200L
        interpolator = AnimationUtils.loadInterpolator(
            context,
            android.R.interpolator.fast_out_linear_in
        )
        addUpdateListener {
            val newTop = it.animatedValue as Int
            drawable.setBounds(left, newTop, right, newTop + height)
        }
        doOnEnd {
            parent.overlay.remove(drawable)
        }
        start()
    }
}

fun View.hideKeyboard() {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showKeyboard() {
    requestFocus()
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}
