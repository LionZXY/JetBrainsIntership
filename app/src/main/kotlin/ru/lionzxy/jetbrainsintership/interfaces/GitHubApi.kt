package ru.lionzxy.jetbrainsintership.interfaces

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.lionzxy.jetbrainsintership.models.SearchResult
import ru.lionzxy.jetbrainsintership.models.User

/**
 * Created by Nikita Kulikov on 28.04.17.
 *
 *
 * Возможно полное или частичное копирование
 */

interface GitHubApi {
    @GET("/users/{username}")
    fun getUser(@Path("username") userName: String): Call<User>

    @GET("/search/users")
    fun search(@Query("q") searchRequest: String, @Query("page") page: Int, @Query("per_page") pageSize: Int): Call<SearchResult>
}
