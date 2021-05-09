package com.hj.smalldecision.ui.home

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
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
            tableKindAdapter!!.submitList(getValidateData(kinds))
            addButton.setOnClickListener {
                var temps = ArrayList<Kind>()
                temps.addAll(tableKindAdapter!!.getData())
                temps.add(Kind(-1, "", true))
                tableKindAdapter!!.submitList(temps)
            }
            saveView.setOnClickListener {
                saveView.isClickable = false
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
                Log.e("333333","--temps="+temps)
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
                Log.e("333333","--kinds="+kinds)
                finish()
                saveView.isClickable = true
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