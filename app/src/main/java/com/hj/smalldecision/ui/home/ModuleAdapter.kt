package com.hj.smalldecision.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hj.smalldecision.databinding.AdapterModuleLayoutBinding
import com.hj.smalldecision.vo.ChooseModule

class ModuleAdapter: ListAdapter<ChooseModule,ModuleAdapter.ModuleViewHolder>(ChooseModule.DIFF_CALLBACK) {

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        this.onItemClickListener = onItemClickListener
    }

    class ModuleViewHolder(var binding: AdapterModuleLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(chooseModule: ChooseModule,onItemClickListener: OnItemClickListener){
            binding.apply {
                numView.text = (position+1).toString()+"."
                titleView.text = chooseModule.title
                rootView.setOnClickListener{
                    onItemClickListener.onClick(chooseModule)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleViewHolder {
        return ModuleViewHolder(AdapterModuleLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ModuleViewHolder, position: Int) {
        holder.bind(currentList[position],onItemClickListener!!)
    }

    interface OnItemClickListener{
        fun onClick(module: ChooseModule)
    }
}