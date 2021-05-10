package com.hj.smalldecision.ui.home

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.google.gson.Gson
import com.hj.goodweight.extension.defaultSharedPreferences
import com.hj.smalldecision.R
import com.hj.smalldecision.databinding.FragmentHomeBinding
import com.hj.smalldecision.ui.settings.SettingsActivity
import com.hj.smalldecision.ui.base.BaseFragment
import com.hj.smalldecision.utils.BeehiveLayoutManager
import com.hj.smalldecision.utils.DataUtils
import com.hj.smalldecision.utils.ViewUtils
import com.hj.smalldecision.weight.TipsDialog
import com.hj.smalldecision.vo.ChooseModule
import com.hj.smalldecision.vo.Kind
import kotlinx.android.synthetic.main.fragment_home.*
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
    private var chooseModuleId = DEFAULT_CHOOSE_MODULE_ID
    private var handler = Handler()
    private var choosePosition = 0
    private var showPosition = 0
    private var loopCount = 0
    private var time = 200L

    companion object{
        const val DECISION_DATA = "decision_data"
        const val CHOOSE_MODULE_ID = "choose_module_id_key"
        const val DEFAULT_CHOOSE_MODULE_ID = 1
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
        chooseModuleId = defaultSharedPreferences.getInt(CHOOSE_MODULE_ID, DEFAULT_CHOOSE_MODULE_ID)
        binding.apply {
            initRecyclerViewSize()
            changeButton.setOnClickListener{
                defaultSharedPreferences.edit().putBoolean("home_model",true).commit()
                Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).navigate(R.id.turn_table_fragment)
            }
            settingsButton.setOnClickListener{
                var intent = Intent(requireContext(), SettingsActivity::class.java)
                startActivity(intent)
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
                        if (!playButton.isClickable) {
                            Toast.makeText(requireContext(), "正在选择中，请在停止后修改内容", Toast.LENGTH_SHORT)
                                .show()
                            return
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
                play()
                binding.playButton.isClickable = false
            }
            resetButton.setOnClickListener {
                binding.playButton.isClickable = true
                reset()
            }
            moduleButton.setOnClickListener{
                lifecycleScope.launch(Dispatchers.IO){
                    var chooseModules= ArrayList<ChooseModule>()
                    chooseModules.addAll(homeViewModel.getChooseModules())
                    withContext(Dispatchers.Main){
                        var moduleDialogFragment = ModuleDialogFragment()
                        moduleDialogFragment.setData(chooseModules)
                        moduleDialogFragment.setOnItemClickListener(object: ModuleDialogFragment.OnItemClickListener{
                            override fun onClick(module: ChooseModule) {
                                reset()
                                chooseModule = module
                                moduleTitleView.text = module.title
                                kinds = DataUtils.getKinds(module.content)
                                chooseModuleId = module.id
                                kindAdapter!!.submitList(kinds)
                                defaultSharedPreferences.edit().putInt(CHOOSE_MODULE_ID,chooseModuleId).commit()
                            }
                        })
                        moduleDialogFragment.setOnDataUpdateListener(object: ModuleDialogFragment.OnDataUpdateListener{
                            override fun onUpdate(module: ChooseModule) {
                                if(chooseModule!!.id == module.id){
                                    chooseModule = module
                                    moduleTitleView.text = module.title
                                    kinds = DataUtils.getKinds(module.content)
                                    chooseModuleId = module.id
                                    kindAdapter!!.submitList(kinds)
                                }
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
                    moduleTitleView.text = chooseModule!!.title
                    kinds.clear()
                    kinds.addAll(contents)
                    kindAdapter!!.submitList(kinds)
                }
            }
        }
    }

    private fun initRecyclerViewSize(){
        var layoutParams = binding.recyclerView.layoutParams
        layoutParams.width = ViewUtils.getMaxWidth(requireContext())
        layoutParams.height = ViewUtils.getMaxWidth(requireContext())
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
        handler.removeCallbacks(runnable)
        choosePosition = 0
        showPosition = 0
        time = 200L
        loopCount = 0
        // binding.playButton.setBackgroundResource(R.drawable.main_color_stroke_bg)
        binding.playButton.text = "开始"
        kindAdapter!!.setShowPosition(-1)
        module_title_view.text = chooseModule!!.title
    }

    private fun play() {
        play_button.isClickable = false
        choosePosition = getRandomChoosePosition()
        showPosition = 0
        time = 200L
        loopCount = 0
        handler.postDelayed(runnable, time)
    }

    private var runnable = object : Runnable {
        override fun run() {
            if (TextUtils.isEmpty(kinds[showPosition].name)) {
                if (showPosition >= kinds.size - 1) {
                    loopCount++
                    showPosition = -1
                }
                showPosition++
                handler.postDelayed(this, 0)
            } else {
                binding.moduleTitleView.text = kinds[showPosition].name
                kindAdapter!!.setShowPosition(showPosition)
                if (loopCount >= 2 && showPosition == choosePosition) {
                    play_button.isClickable = true
                    return
                }
                if (showPosition >= kinds.size - 1) {
                    loopCount++
                    showPosition = -1
                }
                showPosition++
                if (loopCount == 0 && showPosition == kinds.size / 2) {
                    time = 150
                } else if (loopCount == 0 && showPosition == kinds.size * 4 / 5) {
                    time = 80
                } else if (loopCount == 2) {
                    time = 300
                }
                handler.postDelayed(this, time)
            }
        }
    }

    private fun showClearDataDialog(){
        var tipsDialog = TipsDialog.Builder(requireContext())
            .setTitle("提示")
            .setMessage("是否清空该模板的所有选择项？")
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

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(runnable)
    }

}