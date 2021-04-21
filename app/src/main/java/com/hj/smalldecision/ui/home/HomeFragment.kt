package com.hj.smalldecision.ui.home

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.hj.goodweight.extension.defaultSharedPreferences
import com.hj.smalldecision.R
import com.hj.smalldecision.databinding.FragmentHomeBinding
import com.hj.smalldecision.ui.base.BaseFragment
import com.hj.smalldecision.utils.BeehiveLayoutManager
import com.hj.smalldecision.utils.DataUtils
import com.hj.smalldecision.utils.ViewUtils
import com.hj.smalldecision.weight.TipsDialog
import com.hj.smalldecision.vo.ChooseModule
import com.hj.smalldecision.vo.Kind
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class HomeFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val homeViewModel: HomeViewModel by viewModels { viewModelFactory }
    lateinit var binding: FragmentHomeBinding
    private var kindAdapter: KindAdapter? = null
    private var kinds = ArrayList<Kind>()
    private var chooseModule: ChooseModule? = null
    private var buttonAction = STOP
    private var chooseModuleId = DEFAULT_CHOOSE_MODULE_ID
    companion object{
        const val PLAY = 1
        const val RESET = 2
        const val STOP = 3
        const val DECISION_DATA = "decision_data"
        const val CHOOSE_MODULE_ID = "choose_module_id_key"
        const val DEFAULT_CHOOSE_MODULE_ID = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chooseModuleId = defaultSharedPreferences.getInt(CHOOSE_MODULE_ID, DEFAULT_CHOOSE_MODULE_ID)
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
        binding.apply {
            initRecyclerViewSize()
            clearButton.setOnClickListener{
                showClearDataDialog()
            }
            kindAdapter = KindAdapter()
            var beehiveLayoutManager = BeehiveLayoutManager(requireContext(),5)
            beehiveLayoutManager.setFristMarginSecondGroup(0)
            beehiveLayoutManager.setGroupPadding(0)
            recyclerView.layoutManager = beehiveLayoutManager
            recyclerView.adapter = kindAdapter
            kindAdapter!!.setOnItemClickListener(object : KindAdapter.OnItemClickListener {
                override fun onclick(position: Int) {
                    if (kinds[position].name != null && kinds[position].isReal) {
                        if (buttonAction == PLAY) {
                            Toast.makeText(requireContext(), "正在选择中，请在停止后修改内容", Toast.LENGTH_SHORT)
                                .show()
                            return
                        } else if (buttonAction == RESET || buttonAction == STOP) {
                            showPlayButtonAction(RESET)
                        }
                        var editItemDialogFragment = EditItemDialogFragment(kinds[position].name!!)
                        editItemDialogFragment.setOnCommitListener(object :
                            EditItemDialogFragment.OnCommitListener {
                            override fun commit(content: String) {
                                kinds[position].name = content.trim()
                                lifecycleScope.launch(Dispatchers.IO){
                                    chooseModule!!.content = Gson().toJson(kinds)
                                    homeViewModel.addChooseModule(chooseModule!!)
                                    withContext(Dispatchers.Main){
                                        kindAdapter!!.submitList(kinds)
                                    }
                                }
                            }
                        })
                        editItemDialogFragment.show(childFragmentManager, "")
                    }
                }
            })
            playButton.setOnClickListener {
                var kinds_vali = ArrayList<Kind>()
                for(i in 0 until kinds.size){
                    if(!TextUtils.isEmpty(kinds[i].name)){
                        kinds_vali.add(kinds[i])
                    }
                }
                if(kinds_vali.size<3){
                    Toast.makeText(requireContext(), "请至少添加三个选择项", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if(buttonAction==STOP){
                    buttonAction = PLAY
                }
                showPlayButtonAction(buttonAction)
            }
            moduleButton.setOnClickListener{
                lifecycleScope.launch(Dispatchers.IO){
                    var chooseModules= ArrayList<ChooseModule>()
                    chooseModules.addAll(homeViewModel.getChooseModules())
                    withContext(Dispatchers.Main){
                        var moduleDialogFragment = ModuleDialogFragment()
                        for(i in 1..20){
                            chooseModules.add(ChooseModule(i,"火影忍者",""))
                        }
                        moduleDialogFragment.setData(chooseModules)
                        moduleDialogFragment.setOnItemClickListener(object: ModuleDialogFragment.OnItemClickListener{
                            override fun onClick(module: ChooseModule) {
                                chooseModule = module
                                kinds = DataUtils.getKinds(module.content)
                                chooseModuleId = module.id
                                defaultSharedPreferences.edit().putInt(CHOOSE_MODULE_ID,chooseModuleId).commit()
                            }
                        })
                        moduleDialogFragment.show(childFragmentManager,"")
                    }
                }
            }
            lifecycleScope.launch(Dispatchers.IO){
                chooseModule = homeViewModel.getChooseModule(chooseModuleId)
                var contents = DataUtils.getKinds(chooseModule!!.content)
                withContext(Dispatchers.Main){
                    kinds.clear()
                    kinds.addAll(contents)
                    kindAdapter!!.submitList(kinds)
                }
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
        var kinds_vali = ArrayList<Kind>()
        for(i in 0 until kinds.size){
            if(!TextUtils.isEmpty(kinds[i].name)){
                kinds_vali.add(kinds[i])
            }
        }
        var randomNum = Random().nextInt(kinds_vali.size - 1)
        return kinds_vali[randomNum].position
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
                    if (loopCount >= 2 && showPosition == choosePosition) {
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
                    } else if (loopCount == 0 && showPosition == kinds.size * 4 / 5) {
                        time = 100
                    } else if (loopCount == 2) {
                        time = 400
                    }
                    handler.postDelayed(this, time)
                }
            }
        }, time)
    }

    private fun showClearDataDialog(){
        var tipsDialog = TipsDialog.Builder(requireContext())
            .setTitle("提示")
            .setMessage("确定清除所有选择项吗？")
            .setOnCancelListener(object : TipsDialog.OnCancelListener {
                override fun onClick(dialog: Dialog) {
                    dialog.dismiss()
                }
            })
            .setOnPositiveListener(object: TipsDialog.OnPositiveListener {
                override fun onClick(dialog: Dialog) {
                    clearData()
                    kindAdapter!!.submitList(kinds)
                    lifecycleScope.launch(Dispatchers.IO){
                        var content = DataUtils.makeKindsToString(kinds)
                        chooseModule!!.content = content
                        homeViewModel.addChooseModule(chooseModule!!)
                        withContext(Dispatchers.Main){
                            if (buttonAction == RESET || buttonAction == STOP) {
                                showPlayButtonAction(RESET)
                            }
                            dialog.dismiss()
                        }
                    }
                }
            })
            .create()
        tipsDialog.setCancelable(false)
        tipsDialog.show()
    }

    private fun clearData(){
        for(kind in kinds){
            if(kind.isReal){
                kind.name = ""
            }
        }
    }

}