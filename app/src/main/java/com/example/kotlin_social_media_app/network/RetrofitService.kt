package com.example.kotlin_social_media_app.network

import com.example.kotlin_social_media_app.model.UserList
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {
    @GET("users/{name_user}")
    fun searchUser(@Path("name_user") name_user: String): Observable<UserList>
}