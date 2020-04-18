package com.cellstudio.cellvideo.player.cellplayer.models

import com.cellstudio.cellvideo.interactor.model.presentationmodel.SelectionModel

class QualityLevel (
    val trackIndex: Int = -1,
    val groupIndex: Int = -1,
    val rendererIndex: Int = -1,
    val bitrate: Int = -1,
    val label: String = "",
    val width: Int = -1,
    val height: Int = -1
): SelectionModel {
    override fun getText(): String {
        return label
    }
}