package com.ext.android_spot_light_view


import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat

class SpotlightView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val overlayPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val spotlightPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val titlePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var spotlightX = 0f
    private var spotlightY = 0f
    private var spotlightRadius = 0f
    private var currentRadius = 0f

    // Customizable attributes
    private var overlayColor = Color.parseColor("#CC000000")
    private var spotlightShape = SHAPE_CIRCLE
    private var spotlightPadding = 20f
    private var animationDuration = 400L
    private var titleText = ""
    private var descriptionText = ""
    private var titleTextColor = Color.WHITE
    private var descriptionTextColor = Color.WHITE
    private var titleTextSize = 48f
    private var descriptionTextSize = 36f
    private var textMargin = 100f
    private var showAnimation = true

    private var targetView: View? = null
    private var isVisible = false
    private val textBounds = Rect()

    companion object {
        const val SHAPE_CIRCLE = 0
        const val SHAPE_RECTANGLE = 1
        const val SHAPE_ROUNDED_RECTANGLE = 2
    }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SpotlightView,
            0, 0
        ).apply {
            try {
                overlayColor = getColor(R.styleable.SpotlightView_overlayColor, overlayColor)
                spotlightShape = getInt(R.styleable.SpotlightView_spotlightShape, SHAPE_CIRCLE)
                spotlightPadding = getDimension(R.styleable.SpotlightView_spotlightPadding, spotlightPadding)
                animationDuration = getInt(R.styleable.SpotlightView_animationDuration, 400).toLong()
                titleText = getString(R.styleable.SpotlightView_titleText) ?: ""
                descriptionText = getString(R.styleable.SpotlightView_descriptionText) ?: ""
                titleTextColor = getColor(R.styleable.SpotlightView_titleTextColor, titleTextColor)
                descriptionTextColor = getColor(R.styleable.SpotlightView_descriptionTextColor, descriptionTextColor)
                titleTextSize = getDimension(R.styleable.SpotlightView_titleTextSize, titleTextSize)
                descriptionTextSize = getDimension(R.styleable.SpotlightView_descriptionTextSize, descriptionTextSize)
                textMargin = getDimension(R.styleable.SpotlightView_textMargin, textMargin)
                showAnimation = getBoolean(R.styleable.SpotlightView_showAnimation, true)
            } finally {
                recycle()
            }
        }

        setupPaints()
        visibility = GONE
    }

    private fun setupPaints() {
        overlayPaint.apply {
            color = overlayColor
            style = Paint.Style.FILL
        }

        spotlightPaint.apply {
            xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
            style = Paint.Style.FILL
        }

        titlePaint.apply {
            color = titleTextColor
            textSize = titleTextSize
            textAlign = Paint.Align.CENTER
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }

        textPaint.apply {
            color = descriptionTextColor
            textSize = descriptionTextSize
            textAlign = Paint.Align.CENTER
        }
    }

    fun showSpotlight(target: View) {
        targetView = target

        val location = IntArray(2)
        target.getLocationInWindow(location)

        spotlightX = location[0] + target.width / 2f
        spotlightY = location[1] + target.height / 2f

        when (spotlightShape) {
            SHAPE_CIRCLE -> {
                spotlightRadius = (maxOf(target.width, target.height) / 2f) + spotlightPadding
            }
            SHAPE_RECTANGLE, SHAPE_ROUNDED_RECTANGLE -> {
                spotlightRadius = (maxOf(target.width, target.height)) + spotlightPadding
            }
        }

        isVisible = true
        visibility = VISIBLE

        if (showAnimation) {
            animateSpotlight()
        } else {
            currentRadius = spotlightRadius
            invalidate()
        }
    }

    private fun animateSpotlight() {
        ValueAnimator.ofFloat(0f, spotlightRadius).apply {
            duration = animationDuration
            interpolator = DecelerateInterpolator()
            addUpdateListener { animation ->
                currentRadius = animation.animatedValue as Float
                invalidate()
            }
            start()
        }
    }

    fun hideSpotlight() {
        if (showAnimation) {
            ValueAnimator.ofFloat(currentRadius, 0f).apply {
                duration = animationDuration
                interpolator = DecelerateInterpolator()
                addUpdateListener { animation ->
                    currentRadius = animation.animatedValue as Float
                    invalidate()
                    if (currentRadius == 0f) {
                        visibility = GONE
                        isVisible = false
                    }
                }
                start()
            }
        } else {
            visibility = GONE
            isVisible = false
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (!isVisible) return

        val saveCount = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null)

        // Draw overlay
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), overlayPaint)

        // Draw spotlight
        when (spotlightShape) {
            SHAPE_CIRCLE -> {
                canvas.drawCircle(spotlightX, spotlightY, currentRadius, spotlightPaint)
            }
            SHAPE_RECTANGLE -> {
                val rect = RectF(
                    spotlightX - currentRadius,
                    spotlightY - currentRadius,
                    spotlightX + currentRadius,
                    spotlightY + currentRadius
                )
                canvas.drawRect(rect, spotlightPaint)
            }
            SHAPE_ROUNDED_RECTANGLE -> {
                val rect = RectF(
                    spotlightX - currentRadius,
                    spotlightY - currentRadius,
                    spotlightX + currentRadius,
                    spotlightY + currentRadius
                )
                canvas.drawRoundRect(rect, 30f, 30f, spotlightPaint)
            }
        }

        canvas.restoreToCount(saveCount)

        // Draw text
        if (titleText.isNotEmpty() || descriptionText.isNotEmpty()) {
            drawText(canvas)
        }
    }

    private fun drawText(canvas: Canvas) {
        val textY = if (spotlightY > height / 2) {
            spotlightY - currentRadius - textMargin
        } else {
            spotlightY + currentRadius + textMargin
        }

        if (titleText.isNotEmpty()) {
            canvas.drawText(titleText, width / 2f, textY, titlePaint)
        }

        if (descriptionText.isNotEmpty()) {
            val descY = if (titleText.isNotEmpty()) {
                textY + titleTextSize + 20f
            } else {
                textY
            }

            // Multi-line text support
            val lines = descriptionText.split("\n")
            var currentY = descY
            for (line in lines) {
                canvas.drawText(line, width / 2f, currentY, textPaint)
                currentY += descriptionTextSize + 10f
            }
        }
    }

    // Setter methods for programmatic control
    fun setOverlayColor(color: Int) {
        overlayColor = color
        overlayPaint.color = color
        invalidate()
    }

    fun setSpotlightShape(shape: Int) {
        spotlightShape = shape
        invalidate()
    }

    fun setSpotlightPadding(padding: Float) {
        spotlightPadding = padding
        invalidate()
    }

    fun setTitleText(text: String) {
        titleText = text
        invalidate()
    }

    fun setDescriptionText(text: String) {
        descriptionText = text
        invalidate()
    }

    fun setTitleTextColor(color: Int) {
        titleTextColor = color
        titlePaint.color = color
        invalidate()
    }

    fun setDescriptionTextColor(color: Int) {
        descriptionTextColor = color
        textPaint.color = color
        invalidate()
    }
}