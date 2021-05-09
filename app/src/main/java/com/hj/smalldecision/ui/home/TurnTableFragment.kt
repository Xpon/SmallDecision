package com.hj.smalldecision.ui.home

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.hj.goodweight.extension.defaultSharedPreferences
import com.hj.smalldecision.R
import com.hj.smalldecision.databinding.FragmentTurnTableBinding
import com.hj.smalldecision.ui.base.BaseFragment
import com.hj.smalldecision.utils.ColorUtils
import com.hj.smalldecision.utils.DataUtils
import com.hj.smalldecision.vo.ChooseModule
import com.hj.smalldecision.weight.TurntableView
import kotlinx.android.synthetic.main.fragment_turn_table.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TurnTableFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val homeViewModel: HomeViewModel by viewModels { viewModelFactory }
    lateinit var binding: FragmentTurnTableBinding
    private var chooseModuleId = HomeFragment.DEFAULT_CHOOSE_MODULE_ID
    private var chooseModule: ChooseModule? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTurnTableBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chooseModuleId = defaultSharedPreferences.getInt(
            HomeFragment.CHOOSE_MODULE_ID,
            HomeFragment.DEFAULT_CHOOSE_MODULE_ID
        )
        binding.apply {
            changeButton.setOnClickListener {
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                    .navigate(R.id.home_fragment)
            }

            goIv.setOnClickListener {
                turntable.startRotate(object : TurntableView.Callback {
                    override fun onStart() {
                        goIv.isClickable = false
                    }
                    override fun onEnd(pos: Int, name: String) {
                        goIv.isClickable = true
                        moduleTitleView.text = name
                    }
                })
            }
            moduleButton.setOnClickListener{
                lifecycleScope.launch(Dispatchers.IO){
                    var chooseModules= ArrayList<ChooseModule>()
                    chooseModules.addAll(homeViewModel.getChooseModules())
                    withContext(Dispatchers.Main){
                        var moduleDialogFragment = ModuleDialogFragment()
                        moduleDialogFragment.setData(chooseModules)
                        moduleDialogFragment.isTurnTable(true)
                        moduleDialogFragment.setOnItemClickListener(object: ModuleDialogFragment.OnItemClickListener{
                            override fun onClick(module: ChooseModule) {
                                chooseModule = module
                                moduleTitleView.text = module.title
                                var texts = getTableTexts()
                                showTurntable(texts)
                                chooseModuleId = module.id
                                defaultSharedPreferences.edit().putInt(HomeFragment.CHOOSE_MODULE_ID,chooseModuleId).commit()
                            }
                        })
                        moduleDialogFragment.show(childFragmentManager,"")
                    }
                }
            }

        }
        lifecycleScope.launch(Dispatchers.IO) {
            chooseModule = homeViewModel.getChooseModule(chooseModuleId)
            var texts = getTableTexts()
            withContext(Dispatchers.Main) {
                module_title_view.text = chooseModule!!.title
                showTurntable(texts)
            }
        }
    }

    private fun getTableTexts(): Array<String?>{
        var contents = DataUtils.getKinds(chooseModule!!.content)
        var temp = arrayListOf<String>()
        for (content in contents) {
            if (content.isReal && !TextUtils.isEmpty(content.name))
                temp.add(content.name!!)
        }
        var texts = arrayOfNulls<String>(temp.size)
        for(i in temp.indices){
            texts[i] = temp[i]
        }
        return texts
    }

    private fun showTurntable(texts: Array<String?>) {
        var colors = arrayOfNulls<Int>(texts!!.size)
        for (i in colors.indices) {
            colors[i] = ColorUtils.getDefaultChooseColor(requireContext())[i]
        }
        turntable.resetData(colors, texts)
    }
}