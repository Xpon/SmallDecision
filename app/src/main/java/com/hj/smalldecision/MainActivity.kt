package com.hj.smalldecision

import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.view.animation.TranslateAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.hj.smalldecision.animation.TossAnimation
import com.hj.smalldecision.databinding.ActivityMainBinding
import com.hj.smalldecision.ui.base.BaseActivity
import com.hj.smalldecision.weight.TossImageView
import java.util.*


class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            homeButton.setOnClickListener{
                Navigation.findNavController(this@MainActivity,R.id.nav_host_fragment).navigate(R.id.home_fragment)
            }
            coinButton.setOnClickListener{
                Navigation.findNavController(this@MainActivity,R.id.nav_host_fragment).navigate(R.id.coin_fragment)
            }
        }
    }
}