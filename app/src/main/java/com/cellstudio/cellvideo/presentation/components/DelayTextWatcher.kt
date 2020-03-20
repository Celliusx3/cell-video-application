package com.cellstudio.cellvideo.presentation.components

import android.text.Editable
import android.text.TextWatcher
import java.util.*

class DelayTextWatcher(private val listener: Listener): TextWatcher {
    private var timer = Timer()
    private val delay: Long = 300 // milliseconds

    interface Listener {
        fun onTextChanged()
        fun afterTextChanged(editable: Editable?)
    }


    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        listener.onTextChanged()
    }

    override fun afterTextChanged(p0: Editable?) {
        timer.cancel()
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                listener.afterTextChanged(p0)
            }
        }, delay)
    }
}