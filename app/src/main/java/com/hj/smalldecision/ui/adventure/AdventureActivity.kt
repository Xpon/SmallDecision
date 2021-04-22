package com.hj.smalldecision.ui.adventure

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hj.smalldecision.databinding.ActivityAdventureBinding
import com.hj.smalldecision.ui.base.BaseActivity

class AdventureActivity : BaseActivity() {

    lateinit var binding: ActivityAdventureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdventureBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {

        }
    }
}