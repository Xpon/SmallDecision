package com.hj.smalldecision.ui.home

import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hj.smalldecision.R
import com.hj.smalldecision.databinding.FragmentModuleDialogBinding
import com.hj.smalldecision.weight.CustomBottomSheetDialog

class ModuleDialogFragment() : BottomSheetDialogFragment() {

    lateinit var binding: FragmentModuleDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return CustomBottomSheetDialog(requireActivity(),R.style.MyBottomSheetDialog_1)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModuleDialogBinding.inflate(layoutInflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

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
        bottomSheetView.setBackgroundResource(R.color.transparent)
        var layoutParams = bottomSheetView.layoutParams
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams.height = (height*0.8).toInt()
        bottomSheetView.layoutParams = layoutParams
        var bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView)
        bottomSheetBehavior.peekHeight = (height*0.8).toInt()
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

    }
}