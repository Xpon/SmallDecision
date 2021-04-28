package com.hj.smalldecision.ui.settings

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.hj.smalldecision.R
import com.hj.smalldecision.databinding.ActivitySettingsBinding
import com.hj.smalldecision.ui.base.BaseActivity

class SettingsActivity : BaseActivity(),View.OnClickListener {

    lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            backView.setOnClickListener(this@SettingsActivity)
            commentView.setOnClickListener(this@SettingsActivity)
            versionView.text = getVersion()
            userTreatyView.setOnClickListener(this@SettingsActivity)
            privacyView.setOnClickListener(this@SettingsActivity)
            ourView.setOnClickListener(this@SettingsActivity)
        }
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.back_view -> finish()
            R.id.comment_view -> goToMarket()
            R.id.user_treaty_view -> {
                var intent = Intent(this, UserTreatyActivity::class.java)
                startActivity(intent)
            }
            R.id.privacy_view -> {
                var intent = Intent(this, PrivacyActivity::class.java)
                startActivity(intent)
            }
            R.id.our_view -> {
                val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val mClipData = ClipData.newPlainText("已复制邮箱", "741377914@qq.com")
                cm.setPrimaryClip(mClipData)
                Toast.makeText(this,"已复制邮箱", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToMarket(){
        val uri: Uri = Uri.parse("market://details?id=${packageName}")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun getVersion(): String{
        return packageManager.getPackageInfo(packageName,0).versionName
    }
}