package ru.lionzxy.jetbrainsintership.utils

import ru.lionzxy.jetbrainsintership.exceptions.HTTPCodeException
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * Created by Nikita Kulikov on 01.05.17.
 *
 * Возможно полное или частичное копирование
 *
 * Изначально класс планировался как эдакий центр по решению проблем, возникающих в программе и попытки решить их.
 * Плохая идея
 */
class ErrorHelper(val endlessData: EndlessData) : EndlessData.OnChangeQuery, EndlessData.OnDataReceive {
    private var scheduler = Executors.newScheduledThreadPool(1)

    init {
        endlessData.addListener(this)
        endlessData.addListenerQuery(this)
    }

    override fun onChangeQuery(oldQuery: String?, newQuery: String?) {
        scheduler.shutdown()
    }


    override fun onDataReceive(page: Int) {
    }

    override fun onSomeError(e: Throwable) {
        if (scheduler.isShutdown)
            scheduler = Executors.newScheduledThreadPool(1)
        if (e is HTTPCodeException) {
            when (e.code) {
                403 -> this.scheduler.schedule(Runnable { endlessData.loadPage(e.page) }, 20, TimeUnit.SECONDS)
            }
        }

    }

}