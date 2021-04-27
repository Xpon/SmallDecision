package com.hj.smalldecision.ui.home

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
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
        var bottomSheetDialog = CustomBottomSheetDialog(requireActivity(), R.style.MyBottomSheetDialog)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1){
            setWhiteNavigationBar(bottomSheetDialog)
        }
        return bottomSheetDialog
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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setWhiteNavigationBar(dialog: Dialog) {
        val window = dialog.window
        if (window != null) {
            val metrics = DisplayMetrics()
            window.windowManager.defaultDisplay.getMetrics(metrics)
            val dimDrawable = GradientDrawable()
            val navigationBarDrawable = GradientDrawable()
            navigationBarDrawable.shape = GradientDrawable.RECTANGLE
            navigationBarDrawable.setColor(Color.WHITE)
            val layers =
                arrayOf<Drawable>(
                    dimDrawable, navigationBarDrawable
                )
            val windowBackground = LayerDrawable(layers)
            windowBackground.setLayerInsetTop(1, metrics.heightPixels)
            window.setBackgroundDrawable(windowBackground)
        }
    }

    interface OnCommitListener{
        fun commit(content: String)
    }

}