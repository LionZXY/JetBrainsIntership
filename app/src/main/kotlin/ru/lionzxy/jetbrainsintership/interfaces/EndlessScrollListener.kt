package ru.lionzxy.jetbrainsintership.interfaces

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import ru.lionzxy.jetbrainsintership.utils.EndlessData
import java.lang.ref.WeakReference

/**
 * Created by Nikita Kulikov on 29.04.17.
 *
 *
 * Возможно полное или частичное копирование
 */

class EndlessScrollListener(var endlessData: WeakReference<EndlessData>) : RecyclerView.OnScrollListener() {
    private var visibleThreshold = 5

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (endlessData.get() != null) {

            val lastVisibleItemPosition = (recyclerView?.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

            if (lastVisibleItemPosition + visibleThreshold >= endlessData.get()!!.getCount()) {
                endlessData.get()!!.getValue(lastVisibleItemPosition + visibleThreshold)
            }
        }
    }
}