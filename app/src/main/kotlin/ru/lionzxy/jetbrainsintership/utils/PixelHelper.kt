package ru.lionzxy.jetbrainsintership.utils

import android.content.res.Resources
import android.util.TypedValue

/**
 * Created by Nikita Kulikov on 30.04.17.
 *
 *
 * Возможно полное или частичное копирование
 */

object PixelHelper {
    fun pixelFromDP(r: Resources?, dp: Int): Float {
        if (r == null)
            return 0f
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics)
    }
}
