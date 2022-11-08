package com.vados.nasa_photo.utils

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

/**
 * ImageView для отображения фото дня в формате HD
 */
class EquilateralImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val heigh = widthMeasureSpec + widthMeasureSpec/4
        super.onMeasure(widthMeasureSpec, heigh)
    }
}