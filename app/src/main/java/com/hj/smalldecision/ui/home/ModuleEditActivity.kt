package com.hj.smalldecision.ui.home

import android.os.Bundle
import com.hj.smalldecision.databinding.ActivityModuleEditBinding
import com.hj.smalldecision.ui.base.BaseActivity
import com.hj.smalldecision.utils.ViewUtils

class ModuleEditActivity : BaseActivity() {

    lateinit var binding: ActivityModuleEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModuleEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerViewSize()
        binding.apply {

        }
    }

    private fun initRecyclerViewSize(){
        var layoutParams = binding.recyclerView.layoutParams
        layoutParams.width = ViewUtils.getMaxWidth(this)
        layoutParams.height = ViewUtils.getMaxWidth(this)/5*6
        binding.recyclerView.layoutParams = layoutParams
    }
}