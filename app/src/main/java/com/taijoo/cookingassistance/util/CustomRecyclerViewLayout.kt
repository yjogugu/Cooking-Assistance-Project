package com.taijoo.cookingassistance.util

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class CustomRecyclerViewLayout(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs) {

    private val mTouchSlop: Int = ViewConfiguration.get(context).scaledTouchSlop
    private var mPrevX = 0f


    @SuppressLint("Recycle")
    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        when (e.action) {
            MotionEvent.ACTION_DOWN -> mPrevX = MotionEvent.obtain(e).x
            MotionEvent.ACTION_MOVE -> {
                val eventX = e.x
                val xDiff = abs(eventX - mPrevX)
                if (xDiff > mTouchSlop) {
                    return false
                }
            }
        }
        return super.onInterceptTouchEvent(e)
    }

}