package ru.lionzxy.jetbrainsintership.interfaces

import com.arlib.floatingsearchview.FloatingSearchView
import ru.lionzxy.jetbrainsintership.utils.Constants
import java.lang.ref.WeakReference

/**
 * Created by Nikita Kulikov on 30.04.17.
 *
 * Возможно полное или частичное копирование
 */
class HideViewListener(val view: WeakReference<FloatingSearchView>) : android.support.v7.widget.RecyclerView.OnScrollListener() {
    val startTop: Int = view.get()!!.top

    override fun onScrolled(recyclerView: android.support.v7.widget.RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (view.get() != null) {
            var newTop: Int = view.get()!!.top
            newTop -= dy
            var min = (ru.lionzxy.jetbrainsintership.utils.PixelHelper.pixelFromDP(view.get()!!.resources, Constants.SEARCHVIEW_HEIGHT_DP) + startTop).toInt() * -1
            if (newTop > startTop)
                newTop = startTop
            if (newTop < min)
                newTop = min
            view.get()!!.top = newTop
        }
    }
}