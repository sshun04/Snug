package com.shojishunsuke.kibunnsns.customview

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.shojishunsuke.kibunnsns.R


class ExpandableLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    enum class State(val value: Int) {
        COLLAPSED(0),
        COLLAPSING(1),
        EXPANDING(2),
        EXPANDED(3)
    }

    val isViewExpanded :Boolean get() = isExpanded()

    private var isInitialized = false
    private val KEY_SUPER_STATE = "super_state"
    private val KEY_EXPANSION = "expansion"

    private val HORIZONTAL = 0
    private val VERTICAL = 1

    private val DEFAULT_DURATION = 300

    private var duration: Int = DEFAULT_DURATION

    private var expansion: Float = 0f
        set(value) {
            if (field == value) return
            val delta: Float = value - field

            state = when {
                value == 0f -> State.COLLAPSED
                value == 1f -> State.EXPANDED
                delta < 0 -> State.COLLAPSING
                delta > 0 -> State.EXPANDING
                else -> throw IllegalArgumentException()
            }

            visibility = if (state == State.COLLAPSED) View.GONE else View.VISIBLE
            field = value
            requestLayout()


        }

    private var orientation: Int = 0
        set(value) {
            if (value < 0 || value > 1) throw IllegalArgumentException()
            field = value
        }

    private var parallax: Float = 0f
        set(value) {
            field = Math.min(1f, Math.max(0f, value))
        }

    private var state: State = State.COLLAPSED

    private val interpolater = FastOutSlowInInterpolator()
    lateinit var animator: ValueAnimator


    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.ExpandableLayout)
        duration = array.getInt(R.styleable.ExpandableLayout_el_duration, DEFAULT_DURATION)
        expansion = if (array.getBoolean(R.styleable.ExpandableLayout_el_expanded, false)) 1f else 0f
        orientation = array.getInt(R.styleable.ExpandableLayout_android_orientation, VERTICAL)
        parallax = array.getFloat(R.styleable.ExpandableLayout_el_parallax, 1f)
        state = if (expansion == 0f) State.COLLAPSED else State.EXPANDED
        isInitialized = true
    }

    private fun isExpanded(): Boolean = state == State.EXPANDING || state == State.EXPANDED

    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()

        expansion = if (isExpanded()) 1f else 0f

        val bundle = Bundle().apply {
            putFloat(KEY_EXPANSION, expansion)
            putParcelable(KEY_SUPER_STATE, superState)
        }

        return bundle
    }


    override fun onRestoreInstanceState(parcelable: Parcelable?) {
        val bundle = parcelable as Bundle
        expansion = bundle.getFloat(KEY_EXPANSION)
        state = if (expansion == 1f) State.EXPANDED else State.COLLAPSED
        val superState = bundle.getParcelable<Parcelable>(KEY_SUPER_STATE)


        super.onRestoreInstanceState(superState)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width = measuredWidth
        val height = measuredHeight

        val size = if (orientation == LinearLayout.HORIZONTAL) width else height
        visibility = if (expansion == 0f && size == 0) View.GONE else View.VISIBLE


        val expansionDelta = size - Math.round(size * expansion)

        if (parallax > 0) {
            val parallaxDelta = expansionDelta * parallax

            for (i in 0 until childCount) {
                val child = getChildAt(i)
                when (orientation) {
                    HORIZONTAL -> {
                        val direction = 1
                        child.translationX = direction * parallaxDelta
                    }
                    VERTICAL -> {
                        child.translationY = -parallaxDelta
                    }
                }
            }
        }

        when (orientation) {
            HORIZONTAL -> {
                setMeasuredDimension(width - expansionDelta, height)
            }
            VERTICAL -> {
                setMeasuredDimension(width, height - expansionDelta)
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {

//TODO アニメーションの最中に画面回転したらアニメーションキャンセルできるようにする
//        このままだと lateinit property animator has not been initialized

//        animator.cancel()
        super.onConfigurationChanged(newConfig)
    }

    fun toggle(animate: Boolean = true) {
        if (isExpanded()) {
            collapse(animate)
        } else {
            expand(animate)
        }
    }


    fun expand(animate: Boolean = true) {
        setExpanded(true, animate)
    }

    fun collapse(animate: Boolean = true) {
        setExpanded(false, animate)
    }

    private fun setExpanded(expand: Boolean, animate: Boolean = true) {
        if (expand == isExpanded()) return

        val targetExpansion: Int = if (expand) 1 else 0
        if (animate) {
            animateSize(targetExpansion)
        } else {
            expansion = targetExpansion.toFloat()
        }
    }

    private fun animateSize(targetExpansion: Int) {


        animator = ValueAnimator.ofFloat(expansion, targetExpansion.toFloat())
        animator.interpolator = interpolater
        animator.duration = duration.toLong()

        animator.addUpdateListener { valueAnimator ->
            expansion = valueAnimator.animatedValue as Float
        }

        animator.addListener(ExpansionListener(targetExpansion))


        animator.start()

    }


    inner class ExpansionListener(private val targetExpansion: Int) : Animator.AnimatorListener {

        var isCanceled = false

        override fun onAnimationStart(animation: Animator?) {
            state = if (targetExpansion == 0) State.COLLAPSING else State.EXPANDING

        }

        override fun onAnimationEnd(p0: Animator?) {
            if (!isCanceled) {
                state = if (targetExpansion == 0) State.COLLAPSED else State.EXPANDED
                expansion = targetExpansion.toFloat()

            }
        }

        override fun onAnimationCancel(p0: Animator?) {
            isCanceled = true
        }

        override fun onAnimationRepeat(p0: Animator?) {

        }


    }


}