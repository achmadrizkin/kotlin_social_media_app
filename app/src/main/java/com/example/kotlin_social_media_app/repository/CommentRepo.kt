package com.example.kotlin_social_media_app.repository

import androidx.lifecycle.MutableLiveData
import com.example.kotlin_social_media_app.model.comment.Comment
import com.example.kotlin_social_media_app.model.comment.CommentList
import com.example.kotlin_social_media_app.model.explore.ExploreList
import com.example.kotlin_social_media_app.model.product.ProductList
import com.example.kotlin_social_media_app.model.user_auth.UserAuth
import com.example.kotlin_social_media_app.network.RetrofitService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class CommentRepo @Inject constructor(private val retrofitService: RetrofitService) {
    fun getCommentByToUserAndDescAndOrderByCreateAtFromApiCall(postUser: String, toId: String, userList: MutableLiveData<CommentList>) {
        retrofitService.getCommentByToUserAndDesAndToIdOrderByCreateAt(postUser, toId)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getCommentByToUserAndDescAndOrderByCreateAtObserverRx(userList))
    }

    fun postCommentFromApiCall(comment: Comment, exploreList: MutableLiveData<Comment>) {
        retrofitService.postComment(comment)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(postCommentObserverRx(exploreList))
    }

    private fun getCommentByToUserAndDescAndOrderByCreateAtObserverRx(userList: MutableLiveData<CommentList>): Observer<CommentList> {
        return object : Observer<CommentList> {
            override fun onSubscribe(d: Disposable) {
                // start progress indicator
            }

            override fun onNext(t: CommentList) {
                userList.postValue(t)
            }

            override fun onError(e: Throwable) {
                userList.postValue(null)
            }

            override fun onComplete() {
                // end progress indicator
            }
        }
    }

    private fun postCommentObserverRx(userList: MutableLiveData<Comment>): Observer<Comment> {
        return object : Observer<Comment> {
            override fun onSubscribe(d: Disposable) {
                // start progress indicator
            }

            override fun onNext(t: Comment) {
                userList.postValue(t)
            }

            override fun onError(e: Throwable) {
                userList.postValue(null)
            }

            override fun onComplete() {
                // end progress indicator
            }
        }
    }
}