package com.hj.smalldecision

import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.view.animation.TranslateAnimation
import androidx.appcompat.app.AppCompatActivity
import com.hj.smalldecision.animation.TossAnimation
import com.hj.smalldecision.databinding.ActivityMainBinding
import com.hj.smalldecision.weight.TossImageView
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            up.setOnClickListener{
                tiv.cleareOtherAnimation()
                val translateAnimation0 = TranslateAnimation(0f, 0f, 0f, -1000f)
                translateAnimation0.duration = 1500
                val translateAnimation1 = TranslateAnimation(0f, 0f, 0f, 1000f)
                translateAnimation1.duration = 1500
                translateAnimation1.startOffset = 1500
                tiv.setInterpolator(DecelerateInterpolator())
                    .setDuration(3000)
                    .setCircleCount(20)
                    .setXAxisDirection(TossAnimation.DIRECTION_CLOCKWISE)
                    .setYAxisDirection(TossAnimation.DIRECTION_NONE)
                    .setZAxisDirection(TossAnimation.DIRECTION_NONE)
                    .setResult(if (Random().nextInt(2) === 0) TossImageView.RESULT_FRONT else TossImageView.RESULT_REVERSE)
                tiv.addOtherAnimation(translateAnimation0)
                tiv.addOtherAnimation(translateAnimation1)
                tiv.startToss()
            }
            rhombus.setOnClickListener{
                tiv.cleareOtherAnimation()
                val translateAnimation10 = TranslateAnimation(0f, 200f, 0f, -200f)
                translateAnimation10.duration = 2000
                val translateAnimation11 = TranslateAnimation(0f, -200f, 0f, -200f)
                translateAnimation11.duration = 2000
                translateAnimation11.startOffset = 2000
                val translateAnimation12 = TranslateAnimation(0f, -200f, 0f, 200f)
                translateAnimation12.duration = 2000
                translateAnimation12.startOffset = 4000
                val translateAnimation13 = TranslateAnimation(0f, 200f, 0f, 200f)
                translateAnimation13.duration = 2000
                translateAnimation13.startOffset = 6000
                tiv.setInterpolator(LinearInterpolator())
                    .setDuration(8000)
                    .setCircleCount(40)
                    .setXAxisDirection(TossAnimation.DIRECTION_CLOCKWISE)
                    .setYAxisDirection(TossAnimation.DIRECTION_CLOCKWISE)
                    .setZAxisDirection(TossAnimation.DIRECTION_CLOCKWISE)
                    .setResult(if (Random().nextInt(2) === 0) TossImageView.RESULT_FRONT else TossImageView.RESULT_REVERSE)

                tiv.addOtherAnimation(translateAnimation10)
                tiv.addOtherAnimation(translateAnimation11)
                tiv.addOtherAnimation(translateAnimation12)
                tiv.addOtherAnimation(translateAnimation13)
                tiv.startToss()
            }
        }
    }
}