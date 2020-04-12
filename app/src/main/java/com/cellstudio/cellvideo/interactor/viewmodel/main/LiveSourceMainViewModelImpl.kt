package com.cellstudio.cellvideo.interactor.viewmodel.main

//
//class LiveSourceMainViewModelImpl @Inject constructor(scheduler: SchedulerProvider, private val pageInteractor: PageInteractor ): BaseViewModel(scheduler), LiveSourceMainViewModel {
//    private val pageLiveData: MutableLiveData<List<LiveSourcePresentationModel>> = MutableLiveData()
//
//    override fun getViewData(): LiveSourceMainViewModel.ViewData {
//        return object: LiveSourceMainViewModel.ViewData {
//            override fun getLatestLiveSource(): LiveData<List<LiveSourcePresentationModel>> = pageLiveData
//            override val loading: LiveData<Boolean> = loadingLiveData
//        }
//    }
//
//    override fun getViewEvent(): LiveSourceMainViewModel.ViewEvent {
//        return object: LiveSourceMainViewModel.ViewEvent {
//            private fun callLiveSourceAPI(): Observable<List<LiveSourcePresentationModel>> {
//                return pageInteractor.getLiveSource("my")
//                    .map {it.map {LiveSourcePresentationModel.create(it) }}
//            }
//
//            override fun startScreen() {
//                loadingLiveData.value = true
//                compositeDisposable.add(callLiveSourceAPI()
//                    .compose(applySchedulers())
//                    .subscribe ({
//                        pageLiveData.value = it
//                    }, {t ->  t.printStackTrace()})
//                )
//            }
//        }
//
//    }
//}