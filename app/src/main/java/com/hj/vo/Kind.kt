package com.hj.vo

import androidx.recyclerview.widget.DiffUtil
import java.io.Serializable

data class Kind(
    var position: Int,
    var name: String?,
    var isReal: Boolean
):Serializable {
    constructor() : this(0,null,false)
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Kind>() {
            override fun areItemsTheSame(oldItem: Kind, newItem: Kind) =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Kind, newItem: Kind) =
                oldItem == newItem
        }
    }
}