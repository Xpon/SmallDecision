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

    companion object{
        const val HOME = 0
        const val COIN = 1
        const val DICE = 2
    }

    lateinit var binding: ActivityMainBinding
    private var currentClick = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            homeButton.setOnClickListener{
                if(currentClick == HOME){
                    return@setOnClickListener
                }
                currentClick = HOME
                Navigation.findNavController(this@MainActivity,R.id.nav_host_fragment).navigate(R.id.home_fragment)
            }
            coinButton.setOnClickListener{
                if(currentClick == COIN){
                    return@setOnClickListener
                }
                currentClick = COIN
                Navigation.findNavController(this@MainActivity,R.id.nav_host_fragment).navigate(R.id.coin_fragment)
            }
            adventureButton.setOnClickListener{
                if(currentClick == DICE){
                    return@setOnClickListener
                }
                currentClick = DICE
                Navigation.findNavController(this@MainActivity,R.id.nav_host_fragment).navigate(R.id.adventure_fragment)
            }
        }
    }
}