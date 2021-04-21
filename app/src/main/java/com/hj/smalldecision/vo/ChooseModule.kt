package com.hj.smalldecision.vo

import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "choose_module")
data class ChooseModule(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "content")
    var content: String
){
    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ChooseModule>() {
            override fun areItemsTheSame(oldItem: ChooseModule, newItem: ChooseModule) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ChooseModule, newItem: ChooseModule) =
                oldItem == newItem
        }
    }
}
