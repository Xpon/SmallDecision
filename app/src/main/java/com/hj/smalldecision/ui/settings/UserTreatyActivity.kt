package com.hj.smalldecision.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hj.smalldecision.databinding.ActivityUserTreatyBinding
import com.hj.smalldecision.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_user_treaty.*
import java.io.IOException
import java.nio.charset.Charset

class UserTreatyActivity : BaseActivity() {

    lateinit var binding: ActivityUserTreatyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserTreatyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            backView.setOnClickListener{
                finish()
            }
        }
        showUserTreaty()
    }

    private fun showUserTreaty(){
        var filename = "user_treaty.txt"
        try {
            val inputStream = assets.open(filename)
            val lenght = inputStream.available()
            val buffer = ByteArray(lenght)
            inputStream.read(buffer)
            val result = String(buffer, Charset.forName("utf-8"))
            user_treaty_view.text = result
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}