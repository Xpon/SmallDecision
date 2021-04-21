package com.hj.goodweight.extension

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager

inline val Fragment.defaultSharedPreferences: SharedPreferences
    get() = requireContext().defaultSharedPreferences

inline val Context.defaultSharedPreferences: SharedPreferences
    get() = PreferenceManager.getDefaultSharedPreferences(this)