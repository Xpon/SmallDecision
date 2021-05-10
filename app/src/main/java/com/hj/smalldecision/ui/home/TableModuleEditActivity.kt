package com.hj.smalldecision.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hj.smalldecision.R
import com.hj.smalldecision.databinding.ActivityTableModuleEditBinding
import com.hj.smalldecision.ui.base.BaseActivity
import com.hj.smalldecision.utils.ColorUtils
import com.hj.smalldecision.utils.DataUtils
import com.hj.smalldecision.utils.IntentExtras
import com.hj.smalldecision.vo.ChooseModule
import com.hj.smalldecision.vo.Kind
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TableModuleEditActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val homeViewModel: HomeViewModel by viewModels { viewModelFactory }
    lateinit var binding: ActivityTableModuleEditBinding
    private var chooseModule: ChooseModule? = null
    private var kinds = ArrayList<Kind>()
    private var tableKindAdapter: TableKindAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTableModuleEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var bundle = intent.getBundleExtra(ModuleDialogFragment.CHOOSE_MODULE_KEY)
        if (bundle != null) {
            chooseModule =
                bundle.getSerializable(ModuleDialogFragment.CHOOSE_MODULE) as ChooseModule
            kinds.addAll(DataUtils.getKinds(chooseModule!!.content))
        } else {
            chooseModule = ChooseModule(0, "", "")
            kinds.addAll(DataUtils.getDefaultCustomKinds())
        }
        binding.apply {
            moduleNameView.text = chooseModule!!.title
            tableKindAdapter = TableKindAdapter()
            recyclerView.layoutManager = LinearLayoutManager(this@TableModuleEditActivity)
            recyclerView.adapter = tableKindAdapter
            backView.setOnClickListener{
                finish()
            }
            tableKindAdapter!!.setOnDeleteClickListener(object: TableKindAdapter.OnDeleteClickListener{
                override fun onClick(kind: Kind) {
                    var temps = ArrayList<Kind>()
                    temps.addAll(tableKindAdapter!!.getData())
                    if(kind.position!=-1){
                        kinds[kind.position].isReal = false
                        kinds[kind.position].name = ""
                    }
                    temps.remove(kind)
                    tableKindAdapter!!.submitList(temps)
                }
            })
            tableKindAdapter!!.submitList(getValidateData(kinds))
            addButton.setOnClickListener {
                var temps = ArrayList<Kind>()
                temps.addAll(tableKindAdapter!!.getData())
                if(temps.size>ColorUtils.getDefaultChooseColor(this@TableModuleEditActivity).size){
                    return@setOnClickListener
                }
                temps.add(Kind(-1, "", true))
                tableKindAdapter!!.submitList(temps)
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
            saveView.setOnClickListener {
                if (TextUtils.isEmpty(chooseModule!!.title)) {
                    Toast.makeText(this@TableModuleEditActivity, "请输入模板名称", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                var tempKinds = tableKindAdapter!!.getData()
                var temps = ArrayList<Kind>()
                for (i in tempKinds.indices) {
                    var position = tempKinds[i].position
                    if (position == -1) {
                        temps.add(tempKinds[i])
                    } else {
                        kinds[position].name = tempKinds[i].name
                        kinds[position].isReal = true
                    }
                }
                var tempSize = 0
                for (kind in kinds) {
                    if (tempSize > temps.size - 1) {
                        break
                    }
                    if (TextUtils.isEmpty(kind.name)) {
                        kind.name = temps[tempSize].name
                        kind.isReal = true
                        tempSize++
                    }
                }
                var kinds_vali = ArrayList<Kind>()
                for (kind in kinds) {
                    if (kind.isReal&&!TextUtils.isEmpty(kind.name)) {
                        kinds_vali.add(kind)
                    }
                }
                if (kinds_vali.size < 3) {
                    Toast.makeText(this@TableModuleEditActivity, "请至少添加三个选择项", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                lifecycleScope.launch(Dispatchers.IO) {
                    chooseModule!!.content = DataUtils.makeKindsToString(kinds)
                    homeViewModel.addChooseModule(chooseModule!!)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@TableModuleEditActivity, "模板保存成功", Toast.LENGTH_SHORT)
                            .show()
                        var intent = Intent()
                        intent.putExtra(IntentExtras.MODULE_KEY,chooseModule)
                        setResult(IntentExtras.MODULE_RESULT,intent)
                        finish()
                    }
                }
            }
        }
    }

    private fun getValidateData(kinds: List<Kind>): List<Kind> {
        var validateKinds = ArrayList<Kind>()
        for (kind in kinds) {
            if (!TextUtils.isEmpty(kind.name)) {
                validateKinds.add(kind)
            }
        }
        if (validateKinds.size == 0) {
            validateKinds.add(kinds[0])
        }
        return validateKinds
    }
}