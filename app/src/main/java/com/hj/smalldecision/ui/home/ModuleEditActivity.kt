package com.hj.smalldecision.ui.home

import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.hj.smalldecision.R
import com.hj.smalldecision.databinding.ActivityModuleEditBinding
import com.hj.smalldecision.ui.base.BaseActivity
import com.hj.smalldecision.utils.BeehiveLayoutManager
import com.hj.smalldecision.utils.DataUtils
import com.hj.smalldecision.utils.ViewUtils
import com.hj.smalldecision.vo.ChooseModule
import com.hj.smalldecision.vo.Kind
import com.hj.smalldecision.weight.TipsDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ModuleEditActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val homeViewModel: HomeViewModel by viewModels { viewModelFactory }
    lateinit var binding: ActivityModuleEditBinding
    private var chooseModule: ChooseModule? = null
    private var kinds = ArrayList<Kind>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModuleEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        kinds.clear()
        var bundle = intent.getBundleExtra(ModuleDialogFragment.CHOOSE_MODULE_KEY)
        if(bundle!=null){
            chooseModule = bundle.getSerializable(ModuleDialogFragment.CHOOSE_MODULE) as ChooseModule
            kinds.addAll(DataUtils.getKinds(chooseModule!!.content))
        }else {
            chooseModule = ChooseModule(0, "", "")
            kinds.addAll(DataUtils.getDefaultCustomKinds())
        }
        initRecyclerViewSize()
        binding.apply {
            var kindAdapter = CustomKindAdapter()
            var beehiveLayoutManager = BeehiveLayoutManager(this@ModuleEditActivity,5)
            beehiveLayoutManager.setFristMarginSecondGroup(0)
            beehiveLayoutManager.setGroupPadding(0)
            recyclerView.layoutManager = beehiveLayoutManager
            recyclerView.adapter = kindAdapter
            kindAdapter.submitList(kinds!!)
            kindAdapter!!.setOnItemClickListener(object : CustomKindAdapter.OnItemClickListener {
                override fun onclick(position: Int) {
                    var kind = kinds!![position]
                    var kindName = ""
                    if(!TextUtils.isEmpty(kind.name)){
                        kindName = kind.name!!
                    }
                    var editItemDialogFragment = EditItemDialogFragment(kindName)
                    editItemDialogFragment.setOnCommitListener(object: EditItemDialogFragment.OnCommitListener{
                        override fun commit(content: String) {
                            if(!TextUtils.isEmpty(content)){
                                kind.isReal = true
                                kind.name = content
                            }else{
                                kind.isReal = false
                                kind.name = ""
                            }
                            chooseModule!!.content = DataUtils.makeKindsToString(kinds)
                            kindAdapter.submitList(kinds)
                        }
                    })
                    editItemDialogFragment.show(supportFragmentManager,"")
                }
            })
            if(!TextUtils.isEmpty(chooseModule!!.title)){
                moduleNameView.setTextColor(resources.getColor(R.color.black))
                moduleNameView.text = chooseModule!!.title
            }
            moduleNameGroup.setOnClickListener{
                var editItemDialogFragment = EditItemDialogFragment(chooseModule!!.title)
                editItemDialogFragment.setOnCommitListener(object: EditItemDialogFragment.OnCommitListener{
                    override fun commit(content: String) {
                        if(!TextUtils.isEmpty(content)){
                            moduleNameView.setTextColor(resources.getColor(R.color.black))
                            moduleNameView.text = content
                            chooseModule!!.title = content
                        }else{
                            moduleNameView.setTextColor(resources.getColor(R.color.gray1))
                            moduleNameView.text = "输入模板名称"
                            chooseModule!!.title = ""
                        }
                    }
                })
                editItemDialogFragment.show(supportFragmentManager,"")
            }
            saveView.setOnClickListener{
                if(TextUtils.isEmpty(chooseModule!!.title)){
                    Toast.makeText(this@ModuleEditActivity,"请输入模板名称",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if(TextUtils.isEmpty(chooseModule!!.content)){
                    Toast.makeText(this@ModuleEditActivity,"请至少添加三个选择项",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                var kinds_vali = ArrayList<Kind>()
                for(kind in kinds){
                    if(kind.isReal){
                        kinds_vali.add(kind)
                    }
                }
                if(kinds_vali.size<3){
                    Toast.makeText(this@ModuleEditActivity,"请至少添加三个选择项",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                lifecycleScope.launch(Dispatchers.IO){
                    homeViewModel.addChooseModule(chooseModule!!)
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@ModuleEditActivity,"模板保存成功",Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
            backView.setOnClickListener{
                if(!TextUtils.isEmpty(chooseModule!!.title)||!TextUtils.isEmpty(chooseModule!!.content)){
                    showTipsDialog()
                }else{
                    finish()
                }
            }
        }
    }

    private fun showTipsDialog(){
        var tipsDialog = TipsDialog.Builder(this)
            .setTitle("提示")
            .setMessage("模板还未保存，确定退出吗？")
            .setOnCancelListener(object: TipsDialog.OnCancelListener{
                override fun onClick(dialog: Dialog) {
                    dialog.dismiss()
                }
            })
            .setOnPositiveListener(object: TipsDialog.OnPositiveListener{
                override fun onClick(dialog: Dialog) {
                    dialog.dismiss()
                    finish()
                }
            })
            .create()
        tipsDialog.setCancelable(false)
        tipsDialog.show()
    }


    private fun initRecyclerViewSize(){
        var layoutParams = binding.recyclerView.layoutParams
        layoutParams.width = ViewUtils.getMaxWidth(this)
        layoutParams.height = ViewUtils.getMaxWidth(this)/5*6
        binding.recyclerView.layoutParams = layoutParams
    }
}