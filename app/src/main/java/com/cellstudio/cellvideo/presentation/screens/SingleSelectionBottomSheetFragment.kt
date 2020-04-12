package com.cellstudio.cellvideo.presentation.screens

import android.content.DialogInterface
import android.content.res.Resources
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.cellstudio.cellvideo.R
import com.cellstudio.cellvideo.databinding.FragmentSingleSelectionBinding
import com.cellstudio.cellvideo.interactor.model.presentationmodel.SelectionModel
import com.cellstudio.cellvideo.presentation.adapters.SingleSelectionAdapter
import com.cellstudio.cellvideo.presentation.base.BaseBottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_single_selection.*
import kotlinx.android.synthetic.main.fragment_video_player_more.rvMoreList

class SingleSelectionBottomSheetFragment<T: SelectionModel> : BaseBottomSheetDialogFragment() {
    private var binding: FragmentSingleSelectionBinding? = null

    private lateinit var adapter: SingleSelectionAdapter<T>
    var listener: Listener<T>?= null
    var initialSelection: T ?= null
    var selectionsList: List<T> ?= null

    override fun getLayoutResource(): Int {
        return R.layout.fragment_single_selection
    }

    override fun onBindData(view: View) {
        super.onBindData(view)
        dialog?.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheetInternal = d.findViewById<View>(R.id.design_bottom_sheet)
            bottomSheetInternal?.let {
                val maxHeight = Resources.getSystem().displayMetrics.heightPixels
                val height = it.height
                BottomSheetBehavior.from(it).peekHeight = height.coerceAtMost(maxHeight)
            }
        }

        binding = DataBindingUtil.bind(view)
        binding?.lifecycleOwner = this

        listener?.onFragmentReady()
    }

    override fun setupUI() {
        super.setupUI()
        etSearchInput.addTextChangedListener(
            object: TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    val inputText = p0?.toString()
                    if (!(inputText.isNullOrEmpty())) {
                        selectionsList?.let {
                            adapter.setData(it.filter { test -> test.getText().contains(inputText, true) })
                        }
                    } else {
                        selectionsList?.let {
                            adapter.setData(it)
                        }
                    }
                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            } )
        setupAdapter()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        listener?.onHide()
    }

    private fun setupAdapter() {
        if (selectionsList.isNullOrEmpty())
            return

        adapter = SingleSelectionAdapter(selectionsList!!, initialSelection)
        adapter.listener = object: SingleSelectionAdapter.Listener<T>{
            override fun onSelectionClicked(model: T) {
                listener?.onSelected(model)
                this@SingleSelectionBottomSheetFragment.dismiss()
            }
        }
        val layoutManager = LinearLayoutManager(context!!)
        rvMoreList.layoutManager = layoutManager
        rvMoreList.adapter = adapter
    }

    interface Listener<T:SelectionModel> {
        fun onHide()
        fun onFragmentReady()
        fun onSelected(model: T)
    }
}