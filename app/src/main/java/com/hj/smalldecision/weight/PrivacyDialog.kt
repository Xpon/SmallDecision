package com.hj.smalldecision.weight

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.hj.smalldecision.databinding.PrivacyDialogLayoutBinding
import com.hj.smalldecision.ui.settings.PrivacyActivity
import com.hj.smalldecision.ui.settings.UserTreatyActivity
import com.hj.smalldecision.utils.ViewUtils

class PrivacyDialog(private val mContext: Context) : Dialog(mContext) {

    class Builder(private val mContext: Context){

        lateinit var binding: PrivacyDialogLayoutBinding
        private var onPositiveListener: OnPositiveListener? = null
        private var onCancelListener: OnCancelListener? = null

        fun setOnPositiveListener(onPositiveListener: OnPositiveListener): Builder {
            this.onPositiveListener = onPositiveListener
            return this
        }

        fun setOnCancelListener(onCancelListener: OnCancelListener): Builder {
            this.onCancelListener = onCancelListener
            return this
        }

        fun create(): PrivacyDialog{
            var privacyDialog = PrivacyDialog(mContext)
            binding = PrivacyDialogLayoutBinding.inflate(LayoutInflater.from(mContext),null,false)
            privacyDialog.addContentView(binding.root, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT))
            initDialogView(privacyDialog)
            var message = "亲爱的用户，请您务必阅读《隐私政策》和《用户协议》，了解其中条款详情。我们不会在用户未授权的情况下采集、处理或泄露用户信息。本APP不需要开启权限，点击确定即可进入APP，感谢您的使用！"
            var ssb = SpannableStringBuilder(message)
            ssb.setSpan(object: ClickableSpan() {
                override fun onClick(p0: View) {
                    var intent = Intent(mContext,PrivacyActivity::class.java)
                    mContext.startActivity(intent)
                }
                override fun updateDrawState(ds: TextPaint) {
                    ds.color = Color.BLUE
                    ds.isUnderlineText = false
                }
            }, 12, 18, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
            ssb.setSpan(object: ClickableSpan() {
                override fun onClick(p0: View) {
                    var intent = Intent(mContext,UserTreatyActivity::class.java)
                    mContext.startActivity(intent)
                }
                override fun updateDrawState(ds: TextPaint) {
                    ds.color = Color.BLUE
                    ds.isUnderlineText = false
                }
            }, 19, 25, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
            binding.messageView.movementMethod = LinkMovementMethod.getInstance()
            binding.messageView.highlightColor = Color.TRANSPARENT
            binding.messageView.text = ssb
            binding.confirmButton.setOnClickListener{
                onPositiveListener!!.onClick(privacyDialog)
            }
            binding.cancelButton.setOnClickListener{
                onCancelListener!!.onClick(privacyDialog)
            }
            return privacyDialog
        }

        private fun initDialogView(dialog: Dialog){
            dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            val layoutParams = dialog.window!!.attributes
            layoutParams.width =  ViewUtils.getMaxWidth(mContext)*3/4
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