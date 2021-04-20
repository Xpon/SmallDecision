package com.hj.smalldecision.ui.home

import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.hj.smalldecision.R
import com.hj.smalldecision.databinding.FragmentHomeBinding
import com.hj.smalldecision.utils.BeehiveLayoutManager
import com.hj.smalldecision.utils.BeehiveLayoutManager1
import com.hj.smalldecision.utils.ViewUtils
import com.hj.vo.Kind
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private var kindAdapter: KindAdapter? = null
    private var kinds = ArrayList<Kind>()
    private var kinds_vali = ArrayList<Kind>()
    private var buttonAction = STOP
    companion object{
        const val PLAY = 1
        const val RESET = 2
        const val STOP = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        binding.apply {
            initRecyclerViewSize()
            kindAdapter = KindAdapter()
            var beehiveLayoutManager = BeehiveLayoutManager(5, requireContext())
            beehiveLayoutManager.setFristMarginSecondGroup(0)
            beehiveLayoutManager.setGroupPadding(0)
            recyclerView.layoutManager = beehiveLayoutManager
            recyclerView.adapter = kindAdapter
            kindAdapter!!.setOnItemClickListener(object : KindAdapter.OnItemClickListener {
                override fun onclick(position: Int) {
                    if (kinds[position].name != null && kinds[position].isReal) {
                        if(buttonAction == PLAY){
                            Toast.makeText(requireContext(),"正在选择中，请在停止后修改内容",Toast.LENGTH_SHORT).show()
                            return
                        }else if(buttonAction == RESET||buttonAction == STOP){
                            showPlayButtonAction(RESET)
                        }
                        var editItemDialogFragment = EditItemDialogFragment(kinds[position].name!!)
                        editItemDialogFragment.setOnCommitListener(object: EditItemDialogFragment.OnCommitListener{
                            override fun commit(content: String) {
                                kinds[position].name = content.trim()
                                kindAdapter!!.submitList(kinds)
                            }
                        })
                        editItemDialogFragment.show(childFragmentManager, "")
                    }
                }
            })
            kindAdapter!!.submitList(kinds)
            playButton.setOnClickListener {
                if(buttonAction==STOP){
                    buttonAction = PLAY
                }
                showPlayButtonAction(buttonAction)
            }
        }
    }

    private fun showPlayButtonAction(action: Int){
        if(action==PLAY){
            binding.playButton.text = "开始"
            binding.playButton.setBackgroundResource(R.drawable.gray_stroke_bg)
            play()
            binding.playButton.isClickable = false
        }else if(action==RESET){
            binding.playButton.setBackgroundResource(R.drawable.main_color_stroke_bg)
            binding.playButton.text = "开始"
            buttonAction = STOP
            reset()
        }else if(action== STOP){
            binding.playButton.setBackgroundResource(R.drawable.main_color_stroke_bg)
            binding.playButton.text = "重置"
            buttonAction = RESET
            binding.playButton.isClickable = true
        }
    }

    private fun initRecyclerViewSize(){
        var layoutParams = binding.recyclerView.layoutParams
        layoutParams.width = ViewUtils.getMaxWidth(requireContext())
        layoutParams.height = ViewUtils.getMaxWidth(requireContext())/5*6
        binding.recyclerView.layoutParams = layoutParams
    }

    private fun getRandomChoosePosition(): Int{
        for(i in 0 until kinds.size){
            if(!TextUtils.isEmpty(kinds[i].name)){
                kinds_vali.add(kinds[i])
            }
        }
        var randomNum = Random().nextInt(kinds_vali.size-1)
        return kinds_vali[randomNum].position
    }

    private fun initData(){
        kinds.add(Kind(0,"徽菜", true))
        kinds.add(Kind(1,"麻辣小龙虾", true))
        kinds.add(Kind(2,null, false))
        kinds.add(Kind(3,"日式", true))
        kinds.add(Kind(4,"牛排", true))
        kinds.add(Kind(5,null, false))
        kinds.add(Kind(6,null, false))
        kinds.add(Kind(7,"韩式", true))
        kinds.add(Kind(8,"兰州拉面", true))
        kinds.add(Kind(9,null, false))
        kinds.add(Kind(10,"印度菜", true))
        kinds.add(Kind(11,"自己做饭", true))
        kinds.add(Kind(12,null, false))
        kinds.add(Kind(13,null, false))
        kinds.add(Kind(14,"川菜", true))
        kinds.add(Kind(15,"新加坡黑胡椒蟹", true))
        kinds.add(Kind(16,null, false))
        kinds.add(Kind(17,"沙县小吃", true))
        kinds.add(Kind(18,"", true))
        kinds.add(Kind(19,null, false))
        kinds.add(Kind(20,"减肥", true))
        kinds.add(Kind(21,"湘菜", true))
        kinds.add(Kind(22,"米其林三星唐阁粤式餐厅", true))
        kinds.add(Kind(23,null, false))
        kinds.add(Kind(24,"重庆火锅", true))
        kinds.add(Kind(25,"海鲜", true))
        kinds.add(Kind(26,"素食", true))
    }

    private fun reset(){
        binding.chooseView.text = ""
        kindAdapter!!.setShowPosition(-1)
    }

    private fun play() {
        var showPosition = 0
        var loopCount = 0
        var time = 300L
        var handler = Handler()
        var choosePosition = getRandomChoosePosition()
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (TextUtils.isEmpty(kinds[showPosition].name)) {
                    if (showPosition >= kinds.size - 1) {
                        loopCount++
                        showPosition = -1
                    }
                    showPosition++
                    handler.postDelayed(this, 0)
                } else {
                    binding.chooseView.text = kinds[showPosition].name
                    kindAdapter!!.setShowPosition(showPosition)
                    if (loopCount >= 2&&showPosition==choosePosition) {
                        showPlayButtonAction(STOP)
                        return
                    }
                    if (showPosition >= kinds.size - 1) {
                        loopCount++
                        showPosition = -1
                    }
                    showPosition++
                    if (loopCount == 0 && showPosition == kinds.size / 2) {
                        time = 200
                    } else if (loopCount == 0 && showPosition == kinds.size*4/5) {
                        time = 100
                    } else if (loopCount == 2) {
                        time = 400
                    }
                    handler.postDelayed(this, time)
                }
            }
        }, time)
    }



}