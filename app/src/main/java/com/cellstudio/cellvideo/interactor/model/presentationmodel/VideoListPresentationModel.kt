package com.cellstudio.cellvideo.interactor.model.presentationmodel

data class VideoListPresentationModel(
    val title: String?= null,
    val videoList: List<VideoPresentationModel> ?= null,
    val viewType: VideoListViewType
) {
    companion object {
        fun create(title: String?, models: List<VideoPresentationModel>, viewType: VideoListViewType): VideoListPresentationModel {
            return VideoListPresentationModel(
                title = title,
                videoList = models,
                viewType = viewType
            )
        }

        fun create(title: String): VideoListPresentationModel {
            return VideoListPresentationModel(
                title = title,
                viewType = VideoListViewType.VIEW_TYPE_HEADER
            )
        }
    }
}

enum class VideoListViewType(val type: Int) {
    VIEW_TYPE_HEADER(99),
    VIEW_TYPE_GRID(100),
    VIEW_TYPE_LIST(101),
    VIEW_TYPE_RAIL(102),
}