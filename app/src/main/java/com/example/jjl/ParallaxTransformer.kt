package com.example.jjl

import android.view.View
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager
import com.example.jjl.R

class ParallaxTransformer : ViewPager.PageTransformer {
    override fun transformPage(view: View, position: Float) {
        val absPosition = Math.abs(position)
        if (position < -1) {
            view.alpha = 1f
        } else if (position <= 1) {
            val image = view.findViewById<ImageView>(R.id.introImage)
            image?.apply {
                setScaleX(1.0f - absPosition * 2)
                setScaleY(1.0f - absPosition * 2)
                setAlpha(1.0f - absPosition * 2)
            }
            val shadow = view.findViewById<ImageView>(R.id.shadow)
            shadow?.apply {
                setScaleX(1.0f - absPosition * 2)
                setScaleY(1.0f - absPosition * 2)
                setAlpha(1.0f - absPosition * 2)
            }
        } else {
            view.alpha = 1f
        }
    }
}