package com.hj.smalldecision.ui.home

import android.os.Bundle
import android.text.TextUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.hj.smalldecision.databinding.ActivityTableModuleEditBinding
import com.hj.smalldecision.ui.base.BaseActivity
import com.hj.smalldecision.utils.DataUtils
import com.hj.smalldecision.vo.ChooseModule
import com.hj.smalldecision.vo.Kind

class TableModuleEditActivity : BaseActivity() {

    lateinit var binding: ActivityTableModuleEditBinding
    private var chooseModule: ChooseModule? = null
    private var kinds = ArrayList<Kind>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTableModuleEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var bundle = intent.getBundleExtra(ModuleDialogFragment.CHOOSE_MODULE_KEY)
        if(bundle!=null){
            chooseModule = bundle.getSerializable(ModuleDialogFragment.CHOOSE_MODULE) as ChooseModule
            kinds.addAll(DataUtils.getKinds(chooseModule!!.content))
        }else {
            chooseModule = ChooseModule(0, "", "")
            kinds.addAll(DataUtils.getDefaultCustomKinds())
        }
        binding.apply {
            var tableKindAdapter = TableKindAdapter()
            recyclerView.layoutManager = LinearLayoutManager(this@TableModuleEditActivity)
            recyclerView.adapter = tableKindAdapter
            tableKindAdapter.submitList(getValidateData(kinds))
        }
    }

    private fun getValidateData(kinds: List<Kind>): List<Kind>{
        var validateKinds = ArrayList<Kind>()
        for(kind in kinds){
            if(!TextUtils.isEmpty(kind.name)){
                validateKinds.add(kind)
            }
        }
        if(validateKinds.size==0){
            validateKinds.add(kinds[0])
            validateKinds.add(kinds[1])
            validateKinds.add(kinds[2])
        }
        return validateKinds
    }
}