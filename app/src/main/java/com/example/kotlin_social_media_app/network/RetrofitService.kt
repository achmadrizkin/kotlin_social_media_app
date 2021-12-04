package com.example.kotlin_social_media_app.network

import com.example.kotlin_social_media_app.model.ExploreList
import com.example.kotlin_social_media_app.model.UserAuth
import com.example.kotlin_social_media_app.model.UserList
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitService {
    @GET("users/{name_user}")
    fun searchUser(@Path("name_user") name_user: String): Observable<UserList>

    @GET("explore/{user}")
    fun getExploreOrderByLike(@Path("user") user: String): Observable<ExploreList>

    @GET("explore/user/{email}")
    fun getExploreByEmailAndOrderByCreateAt(@Path("email") email: String): Observable<ExploreList>

    @GET("users/email/{email}")
    fun getUserByEmail(@Path("email") email: String): Observable<UserList>

    @POST("users/a/{email_user}")
    fun postUserOrUpdate(@Path("email_user") email_user: String): Observable<UserAuth>
}