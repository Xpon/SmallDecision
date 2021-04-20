package com.hj.smalldecision.ui.home

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hj.smalldecision.R
import com.hj.smalldecision.databinding.FragmentEditItemDialogLayoutBinding
import com.hj.smalldecision.weight.CustomBottomSheetDialog


class EditItemDialogFragment(private val content: String) : BottomSheetDialogFragment() {


    lateinit var binding: FragmentEditItemDialogLayoutBinding
    private var onCommitListener: OnCommitListener?= null

    fun setOnCommitListener(onCommitListener: OnCommitListener){
        this.onCommitListener = onCommitListener
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return CustomBottomSheetDialog(requireActivity(), R.style.MyBottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditItemDialogLayoutBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            contentView.requestFocus()
            contentView.setText(content)
            contentView.setSelection(content.length)
            sendView.setOnClickListener{
                if(onCommitListener!=null)
                    onCommitListener!!.commit(contentView.text.toString())
                dismiss()
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
        var bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView)
        bottomSheetBehavior.peekHeight = (height*0.7).toInt()
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.isHideable = false
    }

    interface OnCommitListener{
        fun commit(content: String)
    }

}