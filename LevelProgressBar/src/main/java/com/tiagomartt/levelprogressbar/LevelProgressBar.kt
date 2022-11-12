package com.tiagomartt.levelprogressbar

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ScaleDrawable
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.tiagomartt.levelprogressbar.databinding.LevelProgressBarBinding

class LevelProgressBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {

    var backgroundColor_ = ContextCompat.getColor(context, R.color.backgroundColor_)
        set(value) {
            field = value
            refreshProgressDrawable()
        }

    var backgroundCornerRadius = context.resources.getDimensionPixelSize(R.dimen.backgroundCornerRadius)
        set(value) {
            field = value
            refreshProgressDrawable()
        }

    // bottom, end, top, start

    var backgroundPadding = context.resources.getDimensionPixelSize(R.dimen.backgroundPaddingBottom_) then context.resources.getDimensionPixelSize(R.dimen.backgroundPaddingEnd_) then context.resources.getDimensionPixelSize(R.dimen.backgroundPaddingTop_) then context.resources.getDimensionPixelSize(R.dimen.backgroundPaddingStart_)
        @RequiresApi(Build.VERSION_CODES.Q)
        set(value) {
            field = value
            refreshProgressDrawable()
        }

    var progressColor = ContextCompat.getColor(context, R.color.progressColor)
        set(value) {
            field = value
            refreshProgressDrawable()
        }

    var progressCornerRadius = context.resources.getDimensionPixelSize(R.dimen.progressCornerRadius)
        set(value) {
            field = value
            refreshProgressDrawable()
        }

    var progressStrokeWidth =  context.resources.getDimensionPixelSize(R.dimen.progressStrokeWidth)
        set(value) {
            field = value
            refreshProgressDrawable()
        }

    var progressStrokeColor = ContextCompat.getColor(context, R.color.progressStrokeColor)
        set(value) {
            field = value
            refreshProgressDrawable()
        }

    var min = 0
        @RequiresApi(Build.VERSION_CODES.O)
        set(value) {
            field = value
            handleMin()
        }

    var progress = 0
        set(value) {
            field = value
            handleProgress()
        }

    var max = 100
        set(value) {
            field = value
            handleMax()
        }

    var textColor = ContextCompat.getColor(context, R.color.textColor)
        set(value) {
            field = value
            handleTextColor()
        }

    var textSizeUnit = TypedValue.COMPLEX_UNIT_PX
        set(value) {
            field = value
            handleTextSize()
        }

    var textSize = context.resources.getDimensionPixelSize(R.dimen.textSize)
        set(value) {
            field = value
            handleTextSize()
        }

    var fontFamilyResId = -1
        set(value) {
            field = value
            handleFontFamily()
        }

    // bottom, end, top, start

    var currentLevelMargin = 0 then 0 then context.resources.getDimensionPixelSize(R.dimen.currentLevelTextMarginTop)
        set(value) {
            field = value
            handleCurrentLevelTextViewMargin()
        }

    var currentPositionMargin = context.resources.getDimensionPixelSize(R.dimen.currentPositionMarginTop) then context.resources.getDimensionPixelSize(R.dimen.currentPositionMarginStart)
        set(value) {
            field = value
            handleCurrentPositionTextView()
        }

    var nextLevelMargin = 0 then context.resources.getDimensionPixelSize(R.dimen.nextLevelMarginEnd) then 0
        set(value) {
            field = value
            handleNextLevelTextViewMargin()
        }

    var nextPositionMargin = context.resources.getDimensionPixelSize(R.dimen.nextPositionMarginTop) then context.resources.getDimensionPixelSize(R.dimen.nextPositionMarginEnd)
        set(value) {
            field = value
            handleNextPositionTextViewMargin()
        }

    var currentLevel = 0
        set(value) {
            field = value
            handleCurrentLevelValue()
        }

    var currentPosition = ""
        set(value) {
            field = value
            handleCurrentPosition()
        }

    var nextLevel = 1
        set(value) {
            field = value
            handleNextLevelValue()
        }

    var nextPosition = ""
        set(value) {
            field = value
            handleNextPositionValue()
        }

    private val binding = LevelProgressBarBinding.inflate(LayoutInflater.from(context), this, true)

    init {

        attrs?.let {

            val attributes = context.obtainStyledAttributes(it, R.styleable.LevelProgressBar)

            backgroundCornerRadius = attributes.getDimensionPixelSize(R.styleable.LevelProgressBar_backgroundCornerRadius, backgroundCornerRadius)
            backgroundColor_ = attributes.getColor(R.styleable.LevelProgressBar_backgroundColor_, backgroundColor_)

            progressCornerRadius = attributes.getDimensionPixelSize(R.styleable.LevelProgressBar_progressCornerRadius, progressCornerRadius)
            progressColor = attributes.getColor(R.styleable.LevelProgressBar_progressColor, progressColor)
            progressStrokeColor = attributes.getColor(R.styleable.LevelProgressBar_progressStrokeColor, progressStrokeColor)
            progressStrokeWidth = attributes.getDimensionPixelSize(R.styleable.LevelProgressBar_progressStrokeWidth, progressStrokeWidth)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                val bpb = attributes.getDimensionPixelSize(R.styleable.LevelProgressBar_backgroundPaddingBottom, backgroundPadding.t1)
                val bpe = attributes.getDimensionPixelSize(R.styleable.LevelProgressBar_backgroundPaddingEnd, backgroundPadding.t2)
                val bpt = attributes.getDimensionPixelSize(R.styleable.LevelProgressBar_backgroundPaddingTop, backgroundPadding.t3)
                val bps = attributes.getDimensionPixelSize(R.styleable.LevelProgressBar_backgroundPaddingStart, backgroundPadding.t4)
                backgroundPadding = NTuple4(bpb, bpe, bpt, bps)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) min = attributes.getInt(R.styleable.LevelProgressBar_min, min)

            progress = attributes.getInt(R.styleable.LevelProgressBar_progress, progress)
            max = attributes.getInt(R.styleable.LevelProgressBar_max, max)

            textColor = attributes.getColor(R.styleable.LevelProgressBar_textColor, textColor)
            textSize = attributes.getDimensionPixelSize(R.styleable.LevelProgressBar_textSize, textSize)
            fontFamilyResId = attributes.getResourceId(R.styleable.LevelProgressBar_fontFamily, -1)

            val clb = attributes.getDimensionPixelSize(R.styleable.LevelProgressBar_currentLevelMarginBottom, currentLevelMargin.t1)
            val clt = attributes.getDimensionPixelSize(R.styleable.LevelProgressBar_currentLevelMarginTop, currentLevelMargin.t2)
            val cls = attributes.getDimensionPixelSize(R.styleable.LevelProgressBar_currentLevelMarginStart, currentLevelMargin.t3)
            currentLevelMargin = NTuple3(clb, clt, cls)

            val cpt = attributes.getDimensionPixelSize(R.styleable.LevelProgressBar_currentPositionMarginTop, currentPositionMargin.t2)
            val cps = attributes.getDimensionPixelSize(R.styleable.LevelProgressBar_currentPositionMarginStart, currentPositionMargin.t1)
            currentPositionMargin = NTuple2(cpt, cps)

            val nlb = attributes.getDimensionPixelSize(R.styleable.LevelProgressBar_nextLevelMarginBottom, nextLevelMargin.t1)
            val nle = attributes.getDimensionPixelSize(R.styleable.LevelProgressBar_nextLevelMarginEnd, nextLevelMargin.t2)
            val nlt = attributes.getDimensionPixelSize(R.styleable.LevelProgressBar_nextLevelMarginTop, nextLevelMargin.t3)
            nextLevelMargin = NTuple3(nlb, nle, nlt)

            val gpe = attributes.getDimensionPixelSize(R.styleable.LevelProgressBar_nextPositionMarginEnd, nextPositionMargin.t1)
            val gpt = attributes.getDimensionPixelSize(R.styleable.LevelProgressBar_nextPositionMarginTop, nextPositionMargin.t2)
            nextPositionMargin = NTuple2(gpe, gpt)

            currentLevel = attributes.getInt(R.styleable.LevelProgressBar_currentLevel, currentLevel)
            attributes.getString(R.styleable.LevelProgressBar_currentPosition)?.let { value ->
                currentPosition = value
            }

            nextLevel = attributes.getInt(R.styleable.LevelProgressBar_nextLevel, nextLevel)
            attributes.getString(R.styleable.LevelProgressBar_nextPosition)?.let { value ->
                nextPosition = value
            }

            attributes.recycle()
        }
    }

    private fun refreshProgressDrawable() {

        // bottom, end, top, start

        val backgroundDrawable = GradientDrawable()
        backgroundDrawable.cornerRadius = backgroundCornerRadius.toFloat()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) backgroundDrawable.setPadding(backgroundPadding.t4, backgroundPadding.t3, backgroundPadding.t2, backgroundPadding.t1)
        backgroundDrawable.setColor(backgroundColor_)

        val progressDrawable =  GradientDrawable()
        progressDrawable.cornerRadius = progressCornerRadius.toFloat() * 0.5f
        progressDrawable.setStroke(progressStrokeWidth, progressStrokeColor)
        progressDrawable.setColor(progressColor)

        val scaleDrawable = ScaleDrawable(progressDrawable, Gravity.START,1f,0.1f)

        val layerList  = LayerDrawable(arrayOf(backgroundDrawable, scaleDrawable))
        layerList.setId(0, android.R.id.background)
        layerList.setId(1, android.R.id.progress)

        binding.progressBar.progressDrawable = layerList
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleMin() {
        binding.progressBar.min = min
    }

    private fun handleProgress() {
        binding.progressBar.progress = progress
    }

    private fun handleMax() {
        binding.progressBar.max = max
    }

    private fun handleTextColor() {
        binding.currentLevelTextView.setTextColor(textColor)
        binding.currentPositionTextView.setTextColor(textColor)
        binding.nextLevelTextView.setTextColor(textColor)
        binding.nextPositionTextView.setTextColor(textColor)
    }

    private fun handleTextSize() {
        binding.currentLevelTextView.setTextSize(textSizeUnit, textSize.toFloat())
        binding.currentPositionTextView.setTextSize(textSizeUnit, textSize.toFloat())
        binding.nextLevelTextView.setTextSize(textSizeUnit, textSize.toFloat())
        binding.nextPositionTextView.setTextSize(textSizeUnit, textSize.toFloat())
    }

    private fun handleFontFamily() {

        if (fontFamilyResId != -1){

            binding.currentLevelTextView.typeface = ResourcesCompat.getFont(context, fontFamilyResId)
            binding.currentPositionTextView.typeface = ResourcesCompat.getFont(context, fontFamilyResId)
            binding.nextLevelTextView.typeface = ResourcesCompat.getFont(context, fontFamilyResId)
            binding.nextPositionTextView.typeface = ResourcesCompat.getFont(context, fontFamilyResId)
        }
    }

    private fun handleCurrentLevelValue() {
        binding.currentLevelTextView.text = currentLevel.toString()
    }

    private fun handleCurrentLevelTextViewMargin() {

        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.root)
        constraintSet.connect(binding.currentLevelTextView.id, ConstraintSet.BOTTOM, binding.progressBar.id, ConstraintSet.BOTTOM, currentLevelMargin.t1)
        constraintSet.connect(binding.currentLevelTextView.id, ConstraintSet.TOP, binding.progressBar.id, ConstraintSet.TOP, currentLevelMargin.t2)
        constraintSet.connect(binding.currentLevelTextView.id, ConstraintSet.START, binding.progressBar.id, ConstraintSet.START, currentLevelMargin.t3)
        constraintSet.applyTo(binding.root)
    }

    private fun handleCurrentPositionTextView() {

        // bottom, end, top, start

        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.root)
        constraintSet.connect(binding.currentPositionTextView.id, ConstraintSet.TOP, binding.progressBar.id, ConstraintSet.BOTTOM, currentPositionMargin.t1)
        constraintSet.connect(binding.currentPositionTextView.id, ConstraintSet.START, binding.progressBar.id, ConstraintSet.START, currentPositionMargin.t2)
        constraintSet.applyTo(binding.root)
    }

    private fun handleNextLevelTextViewMargin() {

        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.root)
        constraintSet.connect(binding.nextLevelTextView.id, ConstraintSet.BOTTOM, binding.progressBar.id, ConstraintSet.BOTTOM, nextLevelMargin.t1)
        constraintSet.connect(binding.nextLevelTextView.id, ConstraintSet.END, binding.progressBar.id, ConstraintSet.END, nextLevelMargin.t2)
        constraintSet.connect(binding.nextLevelTextView.id, ConstraintSet.TOP, binding.progressBar.id, ConstraintSet.TOP, nextLevelMargin.t3)
        constraintSet.applyTo(binding.root)
    }

    private fun handleNextPositionTextViewMargin() {

        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.root)
        constraintSet.connect(binding.nextPositionTextView.id, ConstraintSet.TOP, binding.progressBar.id, ConstraintSet.BOTTOM, nextPositionMargin.t1)
        constraintSet.connect(binding.nextPositionTextView.id, ConstraintSet.END, binding.progressBar.id, ConstraintSet.END, nextPositionMargin.t2)
        constraintSet.applyTo(binding.root)
    }

    private fun handleCurrentPosition() {
        binding.currentPositionTextView.text = currentPosition
    }

    private fun handleNextLevelValue() {
        binding.nextLevelTextView.text = nextLevel.toString()
    }

    private fun handleNextPositionValue() {
        binding.nextPositionTextView.text = nextPosition
    }
}