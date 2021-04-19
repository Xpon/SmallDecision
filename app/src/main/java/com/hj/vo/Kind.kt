package com.hj.vo

import androidx.recyclerview.widget.DiffUtil

data class Kind(
    var name: String
) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Kind>() {
            override fun areItemsTheSame(oldItem: Kind, newItem: Kind) =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Kind, newItem: Kind) =
                oldItem == newItem
        }
    }
}