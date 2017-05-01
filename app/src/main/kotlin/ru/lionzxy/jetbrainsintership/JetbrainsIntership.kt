package ru.lionzxy.jetbrainsintership

import android.app.Application
import android.preference.PreferenceManager
import io.realm.Realm
import ru.lionzxy.jetbrainsintership.utils.Constants


/**
 * Created by Nikita Kulikov on 30.04.17.
 *
 * Возможно полное или частичное копирование
 */
class JetbrainsIntership : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        INSTANCE = this
    }

    companion object Factory {
        private var INSTANCE: JetbrainsIntership? = null
        fun getLastQuery(): String {
            if (JetbrainsIntership.INSTANCE == null)
                return Constants.DEFAULT_QUERY
            return PreferenceManager.getDefaultSharedPreferences(INSTANCE).getString("last_query", Constants.DEFAULT_QUERY)
        }

        fun setLastQuery(query: String?) {
            if (JetbrainsIntership.INSTANCE != null && query != null)
                PreferenceManager.getDefaultSharedPreferences(INSTANCE).edit().putString("last_query", query).apply()
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        INSTANCE = null
    }
}