package com.example.kotlin_social_media_app.network

import com.example.kotlin_social_media_app.model.comment.Comment
import com.example.kotlin_social_media_app.model.comment.CommentList
import com.example.kotlin_social_media_app.model.explore.Explore
import com.example.kotlin_social_media_app.model.explore.ExploreList
import com.example.kotlin_social_media_app.model.like.Like
import com.example.kotlin_social_media_app.model.like.LikeList
import com.example.kotlin_social_media_app.model.product.ProductList
import com.example.kotlin_social_media_app.model.reels.ReelsList
import com.example.kotlin_social_media_app.model.user.User
import com.example.kotlin_social_media_app.model.user.UserList
import com.example.kotlin_social_media_app.model.user_auth.UserAuth
import com.example.kotlin_social_media_app.model.user_following.UserFollowingList
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

    @GET("user/followers/{user_email}")
    fun getUserFollowing(@Path("user_email") user_email: String): Observable<UserFollowingList>

    @GET("explore/home/{email_user}")
    fun getFollowingByEmailUser(@Path("email_user") email_user: String): Observable<ExploreList>

    @GET("post/like/{id}/{email_user}")
    fun getLikeUser(@Path("id") id: String, @Path("email_user") email_user: String): Observable<LikeList>

    @POST("comment/a")
    fun postComment(@Body params: Comment): Observable<Comment>

    @POST("users/a/{email_user}")
    fun postUserOrUpdate(@Path("email_user") email_user: String): Observable<UserAuth>

    @POST("explore/a/post")
    fun postExplore(@Body params: Explore): Observable<Explore>

    @POST("post/like")
    fun postLikeToUserPost(@Body params: Like): Observable<Like>

    @PUT("users/id/{id}")
    fun updateUserProfile(@Path("id") id: String, @Body params: User): Observable<User>
}