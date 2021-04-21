package com.hj.smalldecision.weight

import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.hj.smalldecision.databinding.TipsDialogLayoutBinding
import com.hj.smalldecision.utils.ViewUtils

class TipsDialog(private val mContext: Context) : Dialog(mContext) {

    class Builder(private val mContext: Context){

        lateinit var binding: TipsDialogLayoutBinding
        private var title: String? = null
        private var message: String? = null
        private var onPositiveListener: OnPositiveListener? = null
        private var onCancelListener: OnCancelListener? = null

        fun setTitle(title: String): Builder{
            this.title = title
            return this
        }

        fun setMessage(message: String): Builder{
            this.message = message
            return this
        }

        fun setOnPositiveListener(onPositiveListener: OnPositiveListener): Builder {
            this.onPositiveListener = onPositiveListener
            return this
        }

        fun setOnCancelListener(onCancelListener: OnCancelListener): Builder {
            this.onCancelListener = onCancelListener
            return this
        }

        fun create(): TipsDialog{
            var tipsDialog = TipsDialog(mContext)
            binding = TipsDialogLayoutBinding.inflate(LayoutInflater.from(mContext),null,false)
            tipsDialog.addContentView(binding.root, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT))
            initDialogView(tipsDialog)
            binding.apply {
                if(!TextUtils.isEmpty(title)) titleView.text = title
                if(!TextUtils.isEmpty(message)) messageView.text = message
                if(onPositiveListener!=null) confirmButton.setOnClickListener{
                    onPositiveListener!!.onClick(tipsDialog)
                }
                if(onCancelListener!=null) cancelButton.setOnClickListener{
                    onCancelListener!!.onClick(tipsDialog)
                }
            }
            return tipsDialog
        }

        private fun initDialogView(dialog: Dialog){
            dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            val layoutParams = dialog.window!!.attributes
            layoutParams.width =  ViewUtils.getMaxWidth(mContext)*2/3
//            layoutParams.height =  MeasureUtils.getMaxHeight(mContext)*3/5
            dialog.window!!.attributes = layoutParams
        }
    }

    interface OnPositiveListener{
        abstract fun onClick(dialog: Dialog)
    }

    interface OnCancelListener{
        abstract fun onClick(dialog: Dialog)
    }

}