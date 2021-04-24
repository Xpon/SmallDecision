package com.hj.smalldecision.ui.dice

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
import kotlinx.android.synthetic.main.fragment_dice.*
import java.util.Random

class DiceFragment : Fragment() {

    lateinit var binding: FragmentDiceBinding
    private var handler = Handler()
    private var images = intArrayOf(R.drawable.box1, R.drawable.box2, R.drawable.box3, R.drawable.box4, R.drawable.box5, R.drawable.box6)
    private var diceGroups: Array<FrameLayout>? = null
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
        diceGroups = arrayOf(dice_1_group,dice_2_group,dice_3_group,dice_4_group)
        diceViews = arrayOf(dice_1_view,dice_2_view,dice_3_view,dice_4_view)
        binding.apply {
            diceGroups!![viewNum].visibility = View.VISIBLE
            addDiceView.setOnClickListener{
                viewNum++
                if(viewNum>diceGroups!!.size-1){
                    viewNum = diceGroups!!.size-1
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
        for(i in diceGroups!!.indices){
            if(i<=count){
                diceGroups!![i].visibility = View.VISIBLE
            }else{
                diceGroups!![i].visibility = View.GONE
            }
        }
    }

    private fun removeDiceView(count: Int){
        for(i in diceGroups!!.indices){
            if(i<=count){
                diceGroups!![i].visibility = View.VISIBLE
            }else{
                diceGroups!![i].visibility = View.GONE
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
                diceViews!![i].setImageResource(images[randomResult])
            }
            runTime += 1
            if(runTime>=400){
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