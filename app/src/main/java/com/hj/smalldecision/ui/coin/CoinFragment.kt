package com.hj.smalldecision.ui.coin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.view.animation.TranslateAnimation
import com.hj.smalldecision.R
import com.hj.smalldecision.animation.TossAnimation
import com.hj.smalldecision.databinding.FragmentCoinBinding
import com.hj.smalldecision.ui.settings.SettingsActivity
import com.hj.smalldecision.utils.ViewUtils
import com.hj.smalldecision.weight.TossImageView
import kotlinx.android.synthetic.main.fragment_coin.*
import java.util.*

class CoinFragment : Fragment() {


    lateinit var binding: FragmentCoinBinding
    private var resultFontCount = 0
    private var resultReverseCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCoinBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            var mHeight = (ViewUtils.getMaxHeight(requireContext())*1/2).toFloat()-50
            var mWidth = (ViewUtils.getMaxWidth(requireContext())/4).toFloat()
            settingsButton.setOnClickListener{
                var intent = Intent(requireContext(), SettingsActivity::class.java)
                startActivity(intent)
            }
            resetButton.setOnClickListener{
                resultFontCount = 0
                resultReverseCount = 0
                fontCountView.text = resultFontCount.toString()
                reverseCountView.text = resultReverseCount.toString()
            }
            upButton.setOnClickListener{
                setButtonEnable(false)
                var result = Random().nextInt(2)
                if(result==0){
                    resultFontCount++
                }else{
                    resultReverseCount++
                }
                tiv.cleareOtherAnimation()
                val translateAnimation0 = TranslateAnimation(0f, 0f, 0f, -mHeight)
                translateAnimation0.duration = 1500
                val translateAnimation1 = TranslateAnimation(0f, 0f, 0f, mHeight)
                translateAnimation1.duration = 1500
                translateAnimation1.startOffset = 1500
                tiv.setInterpolator(DecelerateInterpolator())
                    .setDuration(3000)
                    .setCircleCount(40)
                    .setXAxisDirection(TossAnimation.DIRECTION_CLOCKWISE)
                    .setYAxisDirection(TossAnimation.DIRECTION_NONE)
                    .setZAxisDirection(TossAnimation.DIRECTION_NONE)
                    .setResult(if (result == 0) TossImageView.RESULT_FRONT else TossImageView.RESULT_REVERSE)
                tiv.addOtherAnimation(translateAnimation0)
                tiv.addOtherAnimation(translateAnimation1)
                tiv.startToss()
            }
            rhombusButton.setOnClickListener{
                setButtonEnable(false)
                var result = Random().nextInt(2)
                if(result==0){
                    resultFontCount++
                }else{
                    resultReverseCount++
                }
                tiv.cleareOtherAnimation()
                val translateAnimation10 = TranslateAnimation(0f, mWidth, 0f, -mWidth)
                translateAnimation10.duration = 1000
                val translateAnimation11 = TranslateAnimation(0f, -mWidth, 0f, -mWidth)
                translateAnimation11.duration = 1000
                translateAnimation11.startOffset = 1000
                val translateAnimation12 = TranslateAnimation(0f, -mWidth, 0f, mWidth)
                translateAnimation12.duration = 1000
                translateAnimation12.startOffset = 2000
                val translateAnimation13 = TranslateAnimation(0f, mWidth, 0f, mWidth)
                translateAnimation13.duration = 1000
                translateAnimation13.startOffset = 3000
                tiv.setInterpolator(LinearInterpolator())
                    .setDuration(4000)
                    .setCircleCount(40)
                    .setXAxisDirection(TossAnimation.DIRECTION_CLOCKWISE)
                    .setYAxisDirection(TossAnimation.DIRECTION_CLOCKWISE)
                    .setZAxisDirection(TossAnimation.DIRECTION_CLOCKWISE)
                    .setResult(if (result==0) TossImageView.RESULT_FRONT else TossImageView.RESULT_REVERSE)
                tiv.addOtherAnimation(translateAnimation10)
                tiv.addOtherAnimation(translateAnimation11)
                tiv.addOtherAnimation(translateAnimation12)
                tiv.addOtherAnimation(translateAnimation13)
                tiv.startToss()
            }
            tiv.setTossAnimationListener(object: TossAnimation.TossAnimationListener{
                override fun onAnimationStart(p0: Animation?) {
                }
                override fun onAnimationEnd(p0: Animation?) {
                    setButtonEnable(true)
                    fontCountView.text = resultFontCount.toString()
                    reverseCountView.text = resultReverseCount.toString()
                }
                override fun onAnimationRepeat(p0: Animation?) {
                }
                override fun onDrawableChange(result: Int, animation: TossAnimation?) {
                }
            })
        }
    }

    private fun setButtonEnable(isEnable: Boolean){
        if(isEnable){
            up_button.isClickable = true
            rhombus_button.isClickable = true
        }else{
            up_button.isClickable = false
            rhombus_button.isClickable = false
        }
    }

}