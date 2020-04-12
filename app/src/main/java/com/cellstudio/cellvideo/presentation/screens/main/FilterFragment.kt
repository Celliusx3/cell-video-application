package com.cellstudio.cellvideo.presentation.screens.main

//class FilterFragment : BaseBottomSheetDialogFragment() {
//    private var binding: FragmentVideoPlayerMoreBinding? = null
//
//    private lateinit var adapter: SourceAdapter
//    var listener: Listener?= null
//    private var initialSource: DataSource ?= null
//
//    override fun getLayoutResource(): Int {
//        return R.layout.fragment_video_player_more
//    }
//
//    override fun onBindData(view: View) {
//        super.onBindData(view)
//        dialog?.setOnShowListener { dialog ->
//            val d = dialog as BottomSheetDialog
//            val bottomSheetInternal = d.findViewById<View>(R.id.design_bottom_sheet)
//            bottomSheetInternal?.let {
//                val maxHeight = Resources.getSystem().displayMetrics.heightPixels
//                val height = it.height
//                BottomSheetBehavior.from(it).peekHeight = height.coerceAtMost(maxHeight)
//            }
//        }
//
//        binding = DataBindingUtil.bind(view)
//        binding?.lifecycleOwner = this
//
//        listener?.onFragmentReady()
//    }
//
//    override fun setupUI() {
//        super.setupUI()
//        setupAdapter()
//    }
//
//    override fun onCancel(dialog: DialogInterface) {
//        super.onCancel(dialog)
//        listener?.onHide()
//    }
//
//    override fun onGetInputData() {
//        super.onGetInputData()
//        initialSource = arguments?.get(EXTRA_INITIAL_SOURCE) as DataSource
//    }
//
//    private fun setupAdapter() {
//        adapter = SourceAdapter(DataSource.values(), initialSource?: DataSource.M3U)
//        adapter.listener = object: SourceAdapter.Listener{
//            override fun onSourceClicked(model: DataSource) {
//                listener?.onSourceClicked(model)
//                this@SourceSettingsFragment.dismiss()
//            }
//        }
//        val layoutManager = LinearLayoutManager(context!!)
//        rvMoreList.layoutManager = layoutManager
//        rvMoreList.adapter = adapter
//    }
//
//    interface Listener {
//        fun onHide()
//        fun onFragmentReady()
//        fun onSourceClicked(source: DataSource)
//    }
//
//    companion object {
//        const val EXTRA_INITIAL_SOURCE = "EXTRA_INITIAL_SOURCE"
//
//        fun newInstance(initialSource: DataSource): SourceSettingsFragment {
//            val args = Bundle()
//            args.putSerializable(EXTRA_INITIAL_SOURCE, initialSource)
//            val fragment = SourceSettingsFragment()
//            fragment.arguments = args
//            return fragment
//        }
//    }
//
//}