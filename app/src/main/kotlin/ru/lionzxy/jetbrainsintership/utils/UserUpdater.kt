package ru.lionzxy.jetbrainsintership.utils

import retrofit2.*
import ru.lionzxy.jetbrainsintership.interfaces.GitHubApi
import ru.lionzxy.jetbrainsintership.interfaces.OnUserUpdate
import ru.lionzxy.jetbrainsintership.models.User

/**
 * Created by Nikita Kulikov on 01.05.17.
 *
 * Возможно полное или частичное копирование
 *
 * Вынесенная логика получения данных о пользователе
 */
class UserUpdater(val user: User) {
    var listener: OnUserUpdate? = null
    val gitHubApi: GitHubApi? = Retrofit.Builder()
            .baseUrl(Constants.URL) //Базовая часть адреса
            .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
            .build().create(GitHubApi::class.java)

    fun updateData(listener: OnUserUpdate) {
        this.listener = listener
        gitHubApi?.getUser(user.login!!)?.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.body() != null) {
                    response.body().score = user.score
                    listener.onUserUpdate(response.body())
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                listener.onUserUpdate(user)
            }
        })
    }
}