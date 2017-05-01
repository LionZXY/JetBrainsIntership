package ru.lionzxy.jetbrainsintership.exceptions

/**
 * Created by Nikita Kulikov on 01.05.17.
 *
 * Возможно полное или частичное копирование
 */
class HTTPCodeException(val code: Int, val page: Int, val query: String) : RuntimeException() {

}