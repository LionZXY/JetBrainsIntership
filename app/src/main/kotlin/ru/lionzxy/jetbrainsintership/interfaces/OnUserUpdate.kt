package ru.lionzxy.jetbrainsintership.interfaces

import ru.lionzxy.jetbrainsintership.models.User

/**
 * Created by Nikita Kulikov on 01.05.17.
 *
 * Возможно полное или частичное копирование
 */
interface OnUserUpdate {
    fun onUserUpdate(newUser: User);
}