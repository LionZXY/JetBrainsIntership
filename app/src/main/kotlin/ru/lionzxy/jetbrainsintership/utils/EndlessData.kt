package ru.lionzxy.jetbrainsintership.utils

import android.util.Log
import retrofit2.*
import ru.lionzxy.jetbrainsintership.exceptions.HTTPCodeException
import ru.lionzxy.jetbrainsintership.interfaces.GitHubApi
import ru.lionzxy.jetbrainsintership.models.SearchResult
import ru.lionzxy.jetbrainsintership.models.User


/**
 * Created by Nikita Kulikov on 29.04.17.
 *
 * Возможно полное или частичное копирование
 *
 * Основной класс, на котором строиться все приложение.
 * Бесконечный список
 */

class EndlessData {
    public val TAG = "EndlessData"
    private var gitHubApi: GitHubApi? = null
    private var query: String? = null
    private val dataList = ArrayList<User>()
    private var totalCount = -1
    private val listeners = ArrayList<OnDataReceive>()
    private val listenersQuery = ArrayList<OnChangeQuery>()
    private val pageLoadingNow = ArrayList<Int>()

    interface OnDataReceive {
        fun onDataReceive(page: Int)
        fun onSomeError(e: Throwable)
    }

    interface OnChangeQuery {
        fun onChangeQuery(oldQuery: String?, newQuery: String?)
    }

    init {
        gitHubApi = Retrofit.Builder()
                .baseUrl(Constants.URL) //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build().create(GitHubApi::class.java)
    }

    fun getCount(): Int {
        return dataList.size
    }

    /**
     * Гитхаб отказывается выдавать результаты поиска >1000
     */
    fun getTotalSize(): Int {
        return if (totalCount == -1 || totalCount > Constants.SEARCH_LIMIT) Constants.SEARCH_LIMIT else totalCount;
    }


    /**
     * Предпологается, что это основной метод, который надобно дергать.
     *
     * Если @return null и index < getTotalSize(), то ждите ответа в Listener
     */
    fun getValue(index: Int): User? {
        if (index >= getTotalSize())
            return null

        if (dataList.getOrNull(index) == null) {
            loadPage(index / Constants.PER_PAGE)
        }

        return dataList.getOrNull(index)
    }

    fun setQuery(query: String?) {
        if(query == this.query)
            return
        var oldString = this.query
        this.query = query
        totalCount = 0
        dataList.clear()
        pageLoadingNow.clear()
        listenersQuery.forEach { i -> i.onChangeQuery(oldString, query) }
        loadPage(0)
    }


    /**
     * Пагинация у меня в приложении начинается с нуля
     */
    fun loadPage(page: Int) {
        if (pageLoadingNow.contains(page))
            return
        pageLoadingNow.add(page)
        val tmpQuer = query
        if (query != null)
            gitHubApi!!.search(tmpQuer!!, page + 1, Constants.PER_PAGE).enqueue(object : Callback<SearchResult> {
                override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                    //Данные успешно пришли, но надо проверить response.body() на null
                    if (response.body() == null) {
                        Log.d(TAG, "Url: ${call.request().url().toString()}. ErrorText: ${response.errorBody()}. ErrorCode: ${response.code()}")
                        listeners.onEach { a -> a.onSomeError(HTTPCodeException(response.code(), page, tmpQuer)) }
                    } else
                        onDataReceive(tmpQuer, page, response.body())

                    pageLoadingNow.remove(page)
                }

                override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                    //Произошла ошибка
                    listeners.onEach { i -> i.onSomeError(t) }
                    pageLoadingNow.remove(page)
                }
            })
    }

    fun onDataReceive(query: String, page: Int, result: SearchResult) {
        if (this.query != query || result.items == null)
            return

        totalCount = result.total_count
        result.items.onEach { i -> if (!dataList.contains(i)) dataList.add(i) }

        listeners.onEach { i -> i.onDataReceive(page) }
        Log.d(TAG, "Получена страница $page")
    }

    fun addListener(listener: OnDataReceive) {
        if (!listeners.contains(listener))
            listeners.add(listener)
    }

    fun removeListener(listener: OnDataReceive) {
        listeners.remove(listener)
    }

    fun addListenerQuery(listener: OnChangeQuery) {
        if (!listenersQuery.contains(listener))
            listenersQuery.add(listener)
    }

    fun removeListenerQuery(listener: OnChangeQuery) {
        listenersQuery.remove(listener)
    }

    fun onDestroy(){
        listenersQuery.clear()
        listeners.clear()
    }
}
