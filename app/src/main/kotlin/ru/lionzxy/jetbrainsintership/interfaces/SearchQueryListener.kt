package ru.lionzxy.jetbrainsintership.interfaces

import android.view.MenuItem
import android.widget.Toast
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import io.realm.Realm
import io.realm.Sort
import ru.lionzxy.jetbrainsintership.JetbrainsIntership
import ru.lionzxy.jetbrainsintership.R
import ru.lionzxy.jetbrainsintership.models.MySearchSuggestion
import ru.lionzxy.jetbrainsintership.models.SearchQuery
import ru.lionzxy.jetbrainsintership.utils.EndlessData
import java.util.*

/**
 * Created by Nikita Kulikov on 30.04.17.
 *
 * Возможно полное или частичное копирование
 */
class SearchQueryListener(var mSearchView: FloatingSearchView?, val endlessData: EndlessData) : FloatingSearchView.OnQueryChangeListener, FloatingSearchView.OnSearchListener, FloatingSearchView.OnFocusChangeListener, FloatingSearchView.OnMenuItemClickListener {
    val TAG = "SearchListener"
    override fun onFocusCleared() {
        mSearchView!!.setSearchBarTitle(JetbrainsIntership.getLastQuery())
    }

    // Показываем последние 3 запроса поиска
    override fun onFocus() {
        var realmObj = realm?.where(SearchQuery::class.java)?.findAllSorted("date", Sort.DESCENDING)!!
        val lastIndex = if (realmObj.size < 3) realmObj.size - 1 else 2
        if (realmObj.size > 0) {
            val list: ArrayList<MySearchSuggestion> = (0..lastIndex).mapTo(ArrayList()) { MySearchSuggestion(realmObj[it]?.query!!) }
            mSearchView!!.swapSuggestions(list)
        }
    }

    // Ищем и сохраняем в БД запрос
    override fun onSearchAction(currentQuery: String?) {
        if (currentQuery == null || currentQuery.replace(" ", "") == "")
            return
        realm?.beginTransaction()
        val sq = SearchQuery()
        sq.query = currentQuery
        realm?.copyToRealmOrUpdate(sq)
        realm?.commitTransaction()

        JetbrainsIntership.setLastQuery(currentQuery)
        endlessData.setQuery(currentQuery)
    }

    override fun onSuggestionClicked(searchSuggestion: SearchSuggestion?) {
        onSearchAction(searchSuggestion?.body)
    }

    private var realm: Realm? = Realm.getDefaultInstance()
    override fun onSearchTextChanged(oldQuery: String?, newQuery: String?) {
        if (!oldQuery.equals("") && newQuery.equals("")) {
            mSearchView!!.clearSuggestions()
        } else {
            if (!newQuery.equals("")) {
                mSearchView!!.showProgress()

                //Ищем по регулярке запрос
                realm?.where(SearchQuery::class.java)?.like("query", "$newQuery*")?.findAllAsync()?.addChangeListener { collection ->
                    val list: ArrayList<MySearchSuggestion> = ArrayList()
                    collection
                            .filter { it.query != null }
                            .mapTo(list) { MySearchSuggestion(it.query!!) }
                    mSearchView!!.swapSuggestions(list)
                    mSearchView!!.hideProgress()
                }
            } else {
                Toast.makeText(mSearchView?.context, R.string.error_none, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActionMenuItemSelected(item: MenuItem?) {
        if (item?.itemId == R.id.action_search) {
            var query = mSearchView!!.query
            if (mSearchView!!.setSearchFocused(false)) {
                onSearchAction(query)
                mSearchView!!.setSearchBarTitle(query)
                mSearchView!!.clearSuggestions()
            } else mSearchView?.setSearchFocused(true)
        }

    }


    fun onClose() {
        realm?.close()
        mSearchView = null
    }

}