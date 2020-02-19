package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.core.content.ContextCompat
import ru.skillbranch.devintensive.App
import ru.skillbranch.devintensive.R
import kotlin.math.roundToInt

private const val TAG = "CircleImageView"

open class CircleImageView @JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ImageView(ctx, attrs, defStyleAttr, defStyleRes) {

    private var borderWidth = 2.px
    private val clipPath = Path()
    private val clipOval = RectF()
    private val inCircle = RectF()
    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    @ColorInt
    var color = Color.WHITE

    init {
        val ta = context.obtainStyledAttributes(
            attrs,
            R.styleable.CircleImageView,
            defStyleAttr,
            defStyleRes
        )
        try {
            borderWidth =
                ta.getDimension(R.styleable.CircleImageView_cv_borderWidth, borderWidth.toFloat())
                    .roundToInt()
            color = ta.getColor(R.styleable.CircleImageView_cv_borderColor, color)
        } finally {
            ta.recycle()
        }

    }

    override fun onDraw(canvas: Canvas?) {

        canvas?.let { _ ->

            if (width == 0 || height == 0) return

            canvas.clipPath(clipPath.apply {
                addOval(clipOval.apply {
                    left = 0f
                    top = 0f
                    right = width.toFloat()
                    bottom = height.toFloat()
                }, Path.Direction.CCW)
            })

            super.onDraw(canvas)

            val strokeStart = borderWidth / 2F
            val strokeEndRight = width - borderWidth / 2F
            val strokeEndBottom = height - borderWidth / 2F
            if (borderWidth > 0) {
                inCircle.set(strokeStart, strokeStart, strokeEndRight, strokeEndBottom)


                strokePaint.color = color
                strokePaint.style = Paint.Style.STROKE
                strokePaint.strokeWidth = borderWidth.toFloat()

                canvas.drawOval(inCircle, strokePaint)
            }
        }
    }

    @Dimension
    fun getBorderWidth(): Int = borderWidth.dp

    fun setBorderWidth(@Dimension dp: Int) {
        borderWidth = dp.px
        invalidate()
    }

    fun getBorderColor(): Int {
        return color
    }

    fun setBorderColor(hex: String) {
        color = Color.parseColor(hex)
        invalidate()
    }

    fun setBorderColor(@ColorRes colorId: Int) {
        color = ContextCompat.getColor(App.applicationContext(), colorId)
        invalidate()
    }

    val Int.dp: Int
        get() = (this / Resources.getSystem().displayMetrics.density).roundToInt()
    val Int.px: Int
        get() = (this * Resources.getSystem().displayMetrics.density).roundToInt()

}