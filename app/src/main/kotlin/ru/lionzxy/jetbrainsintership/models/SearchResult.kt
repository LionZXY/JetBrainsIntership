package ru.lionzxy.jetbrainsintership.models

/**
 * Created by Nikita Kulikov on 28.04.17.
 *
 *
 * Возможно полное или частичное копирование
 */

class SearchResult(val total_count:Int) {
    public val incomplete_results = false
    public val items: List<User>? = null
}
