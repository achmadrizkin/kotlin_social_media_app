package com.example.kotlin_social_media_app.network

import com.example.kotlin_social_media_app.model.comment.Comment
import com.example.kotlin_social_media_app.model.comment.CommentList
import com.example.kotlin_social_media_app.model.explore.ExploreList
import com.example.kotlin_social_media_app.model.product.ProductList
import com.example.kotlin_social_media_app.model.reels.ReelsList
import com.example.kotlin_social_media_app.model.user.UserList
import com.example.kotlin_social_media_app.model.user_auth.UserAuth
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

import retrofit2.http.POST

import retrofit2.http.Multipart

interface RetrofitService {
    @GET("users/{name_user}")
    fun searchUser(@Path("name_user") name_user: String): Observable<UserList>

    @GET("explore/{user}")
    fun getExploreOrderByLike(@Path("user") user: String): Observable<ExploreList>

    @GET("explore/user/{email}")
    fun getExploreByEmailAndOrderByCreateAt(@Path("email") email: String): Observable<ExploreList>

    @GET("users/email/{email}")
    fun getUserByEmail(@Path("email") email: String): Observable<UserList>

    @GET("products/a/{name}/{email}")
    fun getProductByNameAndEmail(@Path("name") name: String, @Path("email") email: String): Observable<ProductList>

    @GET("comment/{postUser}/{toId}")
    fun getCommentByToUserAndDesAndToIdOrderByCreateAt(@Path("postUser") postUser: String, @Path("toId") toId: String): Observable<CommentList>

    @GET("reels/e/{email}")
    fun getReelsByNotEmailAndOrderByLike(@Path("email") email: String): Observable<ReelsList>

    @GET("products")
    fun getAllProductsList(): Observable<ProductList>

    @GET("products/s/{name_product}")
    fun searchProductByName(@Path("name_product") name_product: String): Observable<ProductList>

    @POST("comment/a")
    fun postComment(@Body params: Comment): Observable<Comment>

    @POST("users/a/{email_user}")
    fun postUserOrUpdate(@Path("email_user") email_user: String): Observable<UserAuth>
}