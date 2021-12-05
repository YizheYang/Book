package com.github.book.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import com.github.book.R

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.11.1 下午 11:16
 * version 1.0
 * update none
 **/
class RadiusCardView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    CardView(context, attrs, defStyleAttr) {

    private var tlRadiu = 0f
    private var trRadiu = 0f
    private var brRadiu = 0f
    private var blRadiu = 0f

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.materialCardViewStyle)

    init {
        radius = 0F
        val array: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.RadiusCardView)
        tlRadiu = array.getDimension(R.styleable.RadiusCardView_rcv_topLeftRadiu, 0f)
        trRadiu = array.getDimension(R.styleable.RadiusCardView_rcv_topRightRadiu, 0f)
        brRadiu = array.getDimension(R.styleable.RadiusCardView_rcv_bottomRightRadiu, 0f)
        blRadiu = array.getDimension(R.styleable.RadiusCardView_rcv_bottomLeftRadiu, 0f)
        background = ColorDrawable()
    }

    override fun onDraw(canvas: Canvas) {
        val path = Path()
        val rectF = getRectF()
        val readius = floatArrayOf(tlRadiu, tlRadiu, trRadiu, trRadiu, brRadiu, brRadiu, blRadiu, blRadiu)
        path.addRoundRect(rectF, readius, Path.Direction.CW)
        canvas.clipPath(path, Region.Op.INTERSECT)
        super.onDraw(canvas)
    }

    private fun getRectF(): RectF {
        val rect = Rect()
        getDrawingRect(rect)
        return RectF(rect)
    }
}