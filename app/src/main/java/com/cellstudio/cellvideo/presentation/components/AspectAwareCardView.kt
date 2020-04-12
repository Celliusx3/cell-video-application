package com.cellstudio.cellvideo.presentation.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.cardview.widget.CardView
import com.cellstudio.cellvideo.R

class AspectAwareCardView : CardView {
    private var aspect = 1f
    private var adjustHeight = true

    enum class Adjust {
        WIDTH, HEIGHT
    }

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    fun setAspect(aspect: Float) {
        this.aspect = aspect
        requestLayout()
    }

    fun setAdjust(adjust: Adjust) {
        this.adjustHeight = Adjust.HEIGHT == adjust
        requestLayout()
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.AspectAwareCardView, 0, 0)
            val aspectWidth = ta.getFloat(R.styleable.AspectAwareCardView_aspect_width, 1f)
            val aspectHeight = ta.getFloat(R.styleable.AspectAwareCardView_aspect_height, 1f)
            aspect = aspectWidth / aspectHeight
            adjustHeight = ta.getInteger(R.styleable.AspectAwareCardView_aspect_adjust, 0) == 0
            ta.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width: Int
        val height: Int

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (adjustHeight) {
            width = View.MeasureSpec.getSize(widthMeasureSpec)
            height = (width / aspect).toInt()
        } else {
            height = View.MeasureSpec.getSize(heightMeasureSpec)
            width = (height * aspect).toInt()
        }
        setMeasuredDimension(width, height)

        super.onMeasure(
            MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        )
    }
}