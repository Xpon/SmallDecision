package com.hj.smalldecision.ui.home

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hj.smalldecision.databinding.AdapterTableKindLayoutBinding
import com.hj.smalldecision.vo.Kind

class TableKindAdapter: ListAdapter<Kind, TableKindAdapter.ViewHolder>(Kind.DIFF_CALLBACK){

    private var onDeleteClickListener: OnDeleteClickListener? = null

    fun getData(): List<Kind>{
        return currentList
    }

    fun setOnDeleteClickListener(onDeleteClickListener: OnDeleteClickListener){
        this.onDeleteClickListener = onDeleteClickListener
    }

    class ViewHolder(var binding: AdapterTableKindLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(kind: Kind){
            binding.apply {
                if(kind.isReal){
                    rootView.visibility = View.VISIBLE
                }else{
                    rootView.visibility = View.GONE
                }
                titleView.setText(kind.name)
                titleView.addTextChangedListener(object: TextWatcher{
                    override fun afterTextChanged(p0: Editable?) {
                    }
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        kind.name = p0.toString()
                    }
                })
                deleteButton.setOnClickListener{

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(AdapterTableKindLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var kind = currentList[position]
        holder.bind(kind)
        if(position==currentList.size-1){
            holder.binding.titleView.requestFocus()
        }
        holder.binding.deleteButton.setOnClickListener {
            onDeleteClickListener!!.onClick(kind)
        }
    }

    interface OnDeleteClickListener{
        fun onClick(kind: Kind)
    }
}