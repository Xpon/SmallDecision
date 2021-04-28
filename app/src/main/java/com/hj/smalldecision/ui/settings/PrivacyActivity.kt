package com.hj.smalldecision.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hj.smalldecision.databinding.ActivityPrivacyBinding
import com.hj.smalldecision.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_privacy.*
import java.io.IOException
import java.nio.charset.Charset

class PrivacyActivity : BaseActivity() {

    lateinit var binding: ActivityPrivacyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrivacyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            backView.setOnClickListener{
                finish()
            }
        }
        showPrivacy()
    }

    private fun showPrivacy(){
        var filename = "privacy_policy.txt"
        try {
            val inputStream = assets.open(filename)
            val lenght = inputStream.available()
            val buffer = ByteArray(lenght)
            inputStream.read(buffer)
            val result = String(buffer, Charset.forName("utf-8"))
            privacy_view.text = result
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}