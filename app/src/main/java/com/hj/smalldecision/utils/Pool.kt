package com.hj.smalldecision.utils

import androidx.collection.SparseArrayCompat

open class Pool<T>(newInstance: New<T>) {
    private val mPool: SparseArrayCompat<T?> = SparseArrayCompat()
    private val mNewInstance: New<T> = newInstance
    operator fun get(key: Int): T? {
        var res = mPool[key]
        if (res == null) {
            res = mNewInstance.get()
            mPool.put(key, res)
        }
        return res
    }

    interface New<T> {
        fun get(): T
    }

}