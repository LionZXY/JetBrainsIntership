package ru.lionzxy.jetbrainsintership.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * Created by Nikita Kulikov on 30.04.17.
 *
 * Возможно полное или частичное копирование
 */
open class SearchQuery : RealmObject() {
    @PrimaryKey
    open var query: String? = null
    open var date: Date? = Date()
}