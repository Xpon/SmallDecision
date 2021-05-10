package com.hj.smalldecision.weight

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import com.hj.smalldecision.R
import com.hj.smalldecision.extension.vibrate
import kotlin.math.min
import kotlin.random.Random

class TurntableView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var count = 6
    private val colors = arrayListOf<Int>()
    private val names = arrayListOf<String>()

    private var vibrateEnable = true

    private var rect: RectF? = null
    private var path: Path? = null

    private var currentAngle = 0f
    private var itemOffset = 0f

    private var lastValue = 0f

    private var rotating = false

    private var callback: Callback? = null

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.TurntableView)
        count = ta.getInteger(R.styleable.TurntableView_count, 6)
        itemOffset = 360f / count
        val colorsArray: Int = ta.getResourceId(R.styleable.TurntableView_colors, R.array.turntable_colors)
        val namesArray: Int = ta.getResourceId(R.styleable.TurntableView_names, R.array.default_eats)
        val colors = context.resources.getStringArray(colorsArray)
        val names = context.resources.getStringArray(namesArray)

        colors.forEach { this.colors.add(Color.parseColor(it)) }
        names.forEach { this.names.add(it) }
//        vibrateEnable = context.defaultSharedPreferences.getBoolean(VIBRATE.first, VIBRATE.second)
        ta.recycle()
    }

    fun resetData(colors: Array<Int?>,namesArray: Array<String?>){
        count = namesArray.size
        itemOffset = 360f / count
        this.colors.clear()
        colors.forEach {
            this.colors.add(it!!)
        }
        this.names.clear()
        namesArray.forEach {
            this.names.add(it!!)
        }
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val squareLen = min(measuredWidth, measuredHeight)
        super.onMeasure(
            MeasureSpec.makeMeasureSpec(squareLen, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(squareLen, MeasureSpec.EXACTLY)
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val w = width.toFloat()
        val h = height.toFloat()
        drawBackground(canvas, w, h)
        drawText(canvas, w, h)
    }

    fun startRotate(callback: Callback) {
        this.callback = callback
        if (rotating) return
        val random = Random.nextInt(count)
        scrollToPos(random)
    }

    private fun scrollToPos(pos: Int) {
        var changePosition = 0
        val endOffset = Random.nextDouble(0.0, 1.0)
        var endAngle: Float = (270 - itemOffset * (pos + endOffset)).toFloat()
        endAngle += if (endAngle < currentAngle) {
            8 * 360
        } else {
            7 * 360
        }
        lastValue = currentAngle
        ValueAnimator.ofFloat(currentAngle, endAngle).apply {
            duration = 4000
            interpolator = DecelerateInterpolator()
            addUpdateListener {
                val value = it.animatedValue as Float
                if (value - lastValue > itemOffset) {
                    // if (vibrateEnable) {
                    //     context.vibrate(longArrayOf(0, 2))
                    // }
                    lastValue = value
                }
                updateRotation(value)
            }
            doOnStart {
                rotating = true
                callback?.onStart()
            }
            doOnEnd {
                rotating = false
                callback?.onEnd(pos, names[pos])
            }
            start()
        }
    }

    private fun updateRotation(rotation: Float) {
        currentAngle = (rotation % 360 + 360) % 360
        invalidate()
    }

    private fun drawBackground(canvas: Canvas, w: Float, h: Float) {
        paint.style = Paint.Style.FILL
        rect = RectF(0f, 0f, w, h)
        var angle = currentAngle
        for (i in 0 until count) {
            val pos = i % colors.size
            paint.color = colors[pos]
            canvas.drawArc(rect!!, angle, itemOffset - 1, true, paint)
            angle += itemOffset
        }
    }

    private fun drawText(canvas: Canvas, w: Float, h: Float) {
        paint.color = Color.WHITE
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = 40f
        if(count>8){
            paint.textSize = 35f
        }else if(count>10){
            paint.textSize = 30f
        }
        paint.letterSpacing = .4f
        rect = RectF(0f, 0f, w, h)
        val fm = paint.fontMetrics
        val textHeight = fm.bottom - fm.top
        var angle = currentAngle
        names.forEach {
            path = Path()
            path!!.addArc(rect!!, angle, itemOffset)
            if(it.length in 4..7){
                var temp1 = it.substring(0,4)
                canvas.drawTextOnPath(temp1, path!!, 0f,  120f, paint)
                var temp2 = it.substring(4,it.length)
                canvas.drawTextOnPath(temp2, path!!, 0f, textHeight + 122, paint)
            }else if(it.length>=8){
                var temp1 = it.substring(0,4)
                canvas.drawTextOnPath(temp1, path!!, 0f,  120f, paint)
                var temp2 = it.substring(4,8)
                canvas.drawTextOnPath(temp2, path!!, 0f, textHeight + 122, paint)
                var temp3 = ""
                if(it.length>=12){
                    temp3 = it.substring(8,12)
                }else{
                    temp3 = it.substring(8,it.length)
                }
                canvas.drawTextOnPath(temp3, path!!, 0f, textHeight*2 + 124, paint)
            }else if(it.length<=4){
                canvas.drawTextOnPath(it, path!!, 0f, 120f, paint)
            }
            angle += itemOffset
        }
    }

    interface Callback {
        fun onStart()

        fun onUpdate(name: String)

        fun onEnd(pos: Int, name: String)
    }
}
