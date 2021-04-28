package com.hj.smalldecision.ui.home

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hj.smalldecision.R
import com.hj.smalldecision.databinding.FragmentModuleDialogBinding
import com.hj.smalldecision.inject.Injectable
import com.hj.smalldecision.ui.base.BaseDialogFragment
import com.hj.smalldecision.ui.base.BaseFragment
import com.hj.smalldecision.utils.DataUtils
import com.hj.smalldecision.utils.IntentExtras
import com.hj.smalldecision.utils.ViewUtils
import com.hj.smalldecision.vo.ChooseModule
import com.hj.smalldecision.vo.Kind
import com.hj.smalldecision.weight.CustomBottomSheetDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ModuleDialogFragment : BottomSheetDialogFragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val homeViewModel: HomeViewModel by viewModels { viewModelFactory }
    private lateinit var binding: FragmentModuleDialogBinding
    private var chooseModules: List<ChooseModule>? = null
    private var onItemClickListener: OnItemClickListener? = null
    private var moduleAdapter: ModuleAdapter? = null
    companion object{
        const val CHOOSE_MODULE_KEY = "choose_module_key"
        const val CHOOSE_MODULE = "choose_module"
    }

    fun setData(chooseModules: List<ChooseModule>){
        this.chooseModules = chooseModules
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        this.onItemClickListener = onItemClickListener
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var bottomSheetDialog = CustomBottomSheetDialog(requireActivity(), R.style.MyBottomSheetDialog_1)
        return bottomSheetDialog
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModuleDialogBinding.inflate(layoutInflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            moduleAdapter = ModuleAdapter()
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = moduleAdapter
            moduleAdapter!!.setOnItemClickListener(object: ModuleAdapter.OnItemClickListener{
                override fun onClick(module: ChooseModule) {
                    onItemClickListener!!.onClick(module)
                    dismiss()
                }
                override fun onEdit(module: ChooseModule) {
                    var intent = Intent(requireContext(),ModuleEditActivity::class.java)
                    var bundle = Bundle()
                    bundle.putSerializable(CHOOSE_MODULE,module)
                    intent.putExtra(CHOOSE_MODULE_KEY,bundle)
                    startActivityForResult(intent,IntentExtras.MODULE_REQUEST)
                }
            })
            if(chooseModules!=null){
                moduleAdapter!!.submitList(chooseModules)
            }
            createModuleButton.setOnClickListener{
                var intent = Intent(requireContext(),ModuleEditActivity::class.java)
                startActivityForResult(intent,IntentExtras.MODULE_REQUEST)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==IntentExtras.MODULE_REQUEST&&resultCode==IntentExtras.MODULE_RESULT){
            lifecycleScope.launch(Dispatchers.IO){
                var chooseModules = homeViewModel.getChooseModules()
                withContext(Dispatchers.Main){
                    moduleAdapter!!.submitList(chooseModules)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val window: Window? = dialog!!.window
        var manager = requireActivity().windowManager
        var outMetrics = DisplayMetrics()
        manager.defaultDisplay.getMetrics(outMetrics)
        var height = outMetrics.heightPixels
        var bottomSheetView = window!!.findViewById<FrameLayout>(R.id.design_bottom_sheet)
        bottomSheetView.setBackgroundResource(R.color.transparent)
        var layoutParams = bottomSheetView.layoutParams
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams.height = (height*0.8).toInt()
        bottomSheetView.layoutParams = layoutParams
        var bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView)
        bottomSheetBehavior.peekHeight = (height*0.8).toInt()
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

    }

    interface OnItemClickListener{
        fun onClick(module: ChooseModule)
    }
}