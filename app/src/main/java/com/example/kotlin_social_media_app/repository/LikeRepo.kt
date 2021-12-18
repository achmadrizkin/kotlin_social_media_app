package com.example.kotlin_social_media_app.repository

import androidx.lifecycle.MutableLiveData
import com.example.kotlin_social_media_app.model.like.Like
import com.example.kotlin_social_media_app.model.like.LikeList
import com.example.kotlin_social_media_app.network.RetrofitService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LikeRepo @Inject constructor(private val retrofitService: RetrofitService) {
    fun postLikeToUserPostFromApiCallFromApiCall(like: Like, userList: MutableLiveData<Like>) {
        retrofitService.postLikeToUserPost(like)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(postLikeToUserPostObserverRx(userList))
    }

    fun getLikeByUserFromApiCall(id: String, email_user: String, userList: MutableLiveData<LikeList>) {
        retrofitService.getLikeUser(id, email_user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getLikeByUserObserverRx(userList))
    }

    private fun postLikeToUserPostObserverRx(userList: MutableLiveData<Like>): Observer<Like> {
        return object : Observer<Like> {
            override fun onSubscribe(d: Disposable) {
                // start progress indicator
            }

            override fun onNext(t: Like) {
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

    private fun getLikeByUserObserverRx(userList: MutableLiveData<LikeList>): Observer<LikeList> {
        return object : Observer<LikeList> {
            override fun onSubscribe(d: Disposable) {
                // start progress indicator
            }

            override fun onNext(t: LikeList) {
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