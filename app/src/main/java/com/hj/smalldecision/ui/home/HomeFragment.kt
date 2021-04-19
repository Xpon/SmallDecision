package com.hj.smalldecision.ui.home

import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hj.smalldecision.databinding.FragmentHomeBinding
import com.hj.smalldecision.utils.BeehiveLayoutManager
import com.hj.smalldecision.utils.BeehiveLayoutManager1
import com.hj.vo.Kind


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private var kindAdapter: KindAdapter? = null
    private var kinds =  ArrayList<Kind>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            for(i in 0..19){
                if(i==0){
                    kinds.add(Kind("米饭"))
                }else if(i==19){
                    kinds.add(Kind("花甲"))
                }else{
                    kinds.add(Kind(""))
                }
            }
            kindAdapter = KindAdapter()
            var beehiveLayoutManager = BeehiveLayoutManager(5,requireContext())
            beehiveLayoutManager.setFristMarginSecondGroup(0)
            beehiveLayoutManager.setGroupPadding(0)
            recyclerView.layoutManager = beehiveLayoutManager
            recyclerView.adapter = kindAdapter
            kindAdapter!!.submitList(kinds)
            playButton.setOnClickListener{
                play()
            }
        }
    }

    private fun play(){
        var showPosition = 0
        var loopCount = 0
        var time = 300L
        var handler = Handler()
        handler.postDelayed(object: Runnable{
            override fun run() {
                if(TextUtils.isEmpty(kinds.get(showPosition).name)){
                    if (showPosition >= 19) {
                        if (loopCount >= 2) {
                            return
                        }
                        loopCount++
                        showPosition = -1
                    }
                    if(loopCount==0&&showPosition==9){
                        time = 200
                    }else if(loopCount==0&&showPosition==15){
                        time = 100
                    }else if(loopCount==2&&showPosition==0){
                        time = 400
                    }
                    showPosition++
                    Log.e("333333","showPosition="+showPosition)
                    handler.postDelayed(this,0)
                }else {
                    kindAdapter!!.setShowPosition(showPosition)
                    if (showPosition >= 19) {
                        if (loopCount >= 2) {
                            return
                        }
                        loopCount++
                        showPosition = -1
                    }
                    showPosition++
                    if(loopCount==0&&showPosition==9){
                        time = 200
                    }else if(loopCount==0&&showPosition==15){
                        time = 100
                    }else if(loopCount==2){
                        time = 400
                    }
                    handler.postDelayed(this, time)
                }
            }
        },time)
    }

}