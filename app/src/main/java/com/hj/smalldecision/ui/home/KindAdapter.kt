package com.hj.smalldecision.ui.home

import android.content.Context
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hj.smalldecision.R
import com.hj.smalldecision.databinding.AdapterSelectBeehiveLayoutBinding
import com.hj.smalldecision.utils.ViewUtils
import com.hj.vo.Kind

class KindAdapter: RecyclerView.Adapter<KindAdapter.KindViewHolder>() {

    private var kinds = ArrayList<Kind>()
    private var mShowPosition = -1

    fun submitList(data: List<Kind>){
        kinds.clear()
        kinds.addAll(data)
        notifyDataSetChanged()
    }

    fun setShowPosition(position: Int){
        mShowPosition = position
        notifyDataSetChanged()
    }

    class KindViewHolder(var binding: AdapterSelectBeehiveLayoutBinding): RecyclerView.ViewHolder(
        binding.root
    ){
        fun bind(kind: Kind){
            binding.apply {
                kindNameView.text = kind.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KindViewHolder {
        return KindViewHolder(
            AdapterSelectBeehiveLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: KindViewHolder, position: Int) {
        var layoutParams = holder.binding.bgView.layoutParams
        layoutParams.width = ViewUtils.getMaxWidth(holder.binding.rootView.context)/5
        layoutParams.height = layoutParams.width
        holder.binding.bgView.layoutParams = layoutParams
        holder.bind(kinds[position])
        if(position==mShowPosition){
            holder.binding.bgView.setImageResource(R.color.white)
        }else{
            holder.binding.bgView.setImageResource(R.mipmap.beehive)
        }
    }

    override fun getItemCount(): Int {
        return kinds.size
    }
}