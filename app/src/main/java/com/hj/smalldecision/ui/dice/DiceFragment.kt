package com.hj.smalldecision.ui.dice

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.hj.smalldecision.R
import com.hj.smalldecision.databinding.FragmentDiceBinding
import com.hj.smalldecision.ui.settings.SettingsActivity
import kotlinx.android.synthetic.main.fragment_dice.*
import java.util.Random

class DiceFragment : Fragment() {

    lateinit var binding: FragmentDiceBinding
    private var handler = Handler()
    private var images = intArrayOf(R.mipmap.box_1, R.mipmap.box_2, R.mipmap.box_3, R.mipmap.box_4, R.mipmap.box_5, R.mipmap.box_6)
    private var diceViews: Array<ImageView>? = null
    private var viewNum = 0
    private var runTime = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiceBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewNum = 0
        diceViews = arrayOf(dice_1_view,dice_2_view,dice_3_view,dice_4_view,dice_5_view,dice_6_view)
        binding.apply {
            settingsButton.setOnClickListener{
                var intent = Intent(requireContext(),SettingsActivity::class.java)
                startActivity(intent)
            }
            diceViews!![viewNum].visibility = View.VISIBLE
            addDiceView.setOnClickListener{
                viewNum++
                if(viewNum>diceViews!!.size-1){
                    viewNum = diceViews!!.size-1
                    return@setOnClickListener
                }
                addDiceView(viewNum)
            }
            removeDiceView.setOnClickListener{
                viewNum--
                if(viewNum<0){
                    viewNum = 0
                    return@setOnClickListener
                }
                removeDiceView(viewNum)
            }
            startButton.setOnClickListener{
                playDice()
            }
        }
    }

    private fun addDiceView(count: Int){
        for(i in diceViews!!.indices){
            if(i<=count){
                diceViews!![i].visibility = View.VISIBLE
            }else{
                diceViews!![i].visibility = View.GONE
            }
        }
    }

    private fun removeDiceView(count: Int){
        for(i in diceViews!!.indices){
            if(i<=count){
                diceViews!![i].visibility = View.VISIBLE
            }else{
                diceViews!![i].visibility = View.GONE
            }
        }
    }

    private fun playDice(){
        runTime = 0
        handler.postDelayed(runnable,0)
    }

    private var runnable = object: Runnable {
        override fun run() {
            for(i in 0..viewNum){
                var randomResult = Random().nextInt(6)
                diceViews!![i].setBackgroundResource(images[randomResult])
            }
            runTime += 1
            if(runTime>=100){
                return
            }
            handler.postDelayed(this,10)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(runnable)
    }
}