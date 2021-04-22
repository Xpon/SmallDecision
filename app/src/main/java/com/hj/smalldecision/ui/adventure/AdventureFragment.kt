package com.hj.smalldecision.ui.adventure

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hj.smalldecision.R
import com.hj.smalldecision.databinding.FragmentAdventureBinding
import kotlinx.android.synthetic.main.fragment_adventure.*
import java.io.IOException
import java.util.Random

class AdventureFragment : Fragment() {

    lateinit var binding: FragmentAdventureBinding
    private var handler = Handler()
    var images = intArrayOf(R.drawable.box1, R.drawable.box2, R.drawable.box3, R.drawable.box4, R.drawable.box5, R.drawable.box6)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdventureBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            startButton.setOnClickListener{
                playDice()
            }
        }
    }

    private fun playDice(){
        var runTime = 0
        handler.postDelayed(object: Runnable {
            override fun run() {
                var randomResult1 = Random().nextInt(6)
                var randomResult2 = Random().nextInt(6)
                var randomResult3 = Random().nextInt(6)
                var randomResult4 = Random().nextInt(6)
                dice_1_view.setImageResource(images[randomResult1])
                dice_2_view.setImageResource(images[randomResult2])
                dice_3_view.setImageResource(images[randomResult3])
                dice_4_view.setImageResource(images[randomResult4])
                runTime += 1
                if(runTime>=100){
                    Log.e("333333","randomResult1="+randomResult1+"...randomResult2="+randomResult2+
                    "...randomResult3="+randomResult3+"...randomResult4="+randomResult4)
                    return
                }
                handler.postDelayed(this,10)
            }
        },0)
    }
}