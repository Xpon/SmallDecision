package com.hj.smalldecision.ui.home

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hj.smalldecision.R
import com.hj.smalldecision.databinding.AdapterSelectBeehiveLayoutBinding
import com.hj.smalldecision.utils.ViewUtils
import com.hj.smalldecision.vo.Kind

class KindAdapter: RecyclerView.Adapter<KindAdapter.KindViewHolder>() {

    private var kinds = ArrayList<Kind>()
    private var mShowPosition = -1
    private var onItemClickListener: OnItemClickListener? = null

    fun submitList(data: List<Kind>){
        kinds.clear()
        kinds.addAll(data)
        notifyDataSetChanged()
    }

    fun setShowPosition(position: Int){
        mShowPosition = position
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        this.onItemClickListener = onItemClickListener
    }

    class KindViewHolder(var binding: AdapterSelectBeehiveLayoutBinding): RecyclerView.ViewHolder(
        binding.root
    ){
        fun bind(kind: Kind){
            binding.apply {
                if(kind.name==null&&!kind.isReal) {
                    bgView.setImageResource(R.color.transparent)
                }else{
                    bgView.setImageResource(R.mipmap.beehive)
                }
                if(!TextUtils.isEmpty(kind.name)){
                    when {
                        kind.name!!.length<=2 -> {
                            kindNameView.textSize = 18f
                        }
                        kind.name!!.length in 3..5 -> {
                            kindNameView.textSize = 14f
                        }
                        kind.name!!.length in 6..8 -> {
                            kindNameView.textSize = 12f
                        }
                        kind.name!!.length>8 -> {
                            kindNameView.textSize = 10f
                        }
                    }
                }
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
        var layoutParams1 = holder.binding.chooseView.layoutParams
        layoutParams1.width = layoutParams.width
        layoutParams1.height = layoutParams.height
        holder.binding.chooseView.layoutParams = layoutParams
        var layoutParams2 = holder.binding.kindNameView.layoutParams
        layoutParams2.width = layoutParams.width*3/4
        layoutParams2.height = layoutParams2.width
        holder.binding.kindNameView.layoutParams = layoutParams2

        holder.bind(kinds[position])
        if(position==mShowPosition){
            holder.binding.chooseView.setImageResource(R.mipmap.home_choose_icon)
            holder.binding.chooseView.visibility = View.VISIBLE
        }else{
            holder.binding.chooseView.visibility = View.GONE
        }
        holder.binding.bgView.setOnClickListener{
            if(onItemClickListener!=null)
                onItemClickListener!!.onclick(position)
        }
    }

    override fun getItemCount(): Int {
        return kinds.size
    }

    interface OnItemClickListener{
        fun onclick(position: Int)
    }
}