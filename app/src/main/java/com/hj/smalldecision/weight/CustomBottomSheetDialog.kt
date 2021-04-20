package com.hj.smalldecision.weight

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog

class CustomBottomSheetDialog(private val activity: Activity,theme: Int): BottomSheetDialog(activity,theme) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var screenHeight = getScreenHeight(activity)
        var statusBarHeight = getStatusBarHeight(activity)
        var dialogHeight = screenHeight - statusBarHeight
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
            if(dialogHeight == 0) {
                ViewGroup.LayoutParams.MATCH_PARENT
            }else{
                dialogHeight
            })
    }

    private fun getScreenHeight(activity: Activity): Int{
        var displaymetrics =  DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displaymetrics);
        return displaymetrics.heightPixels
    }

    private fun getStatusBarHeight(context: Context): Int {
        var statusBarHeight = 0
        var resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = context.resources.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

}