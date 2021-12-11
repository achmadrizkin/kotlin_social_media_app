package com.example.kotlin_social_media_app.repository

import androidx.lifecycle.MutableLiveData
import com.example.kotlin_social_media_app.model.comment.CommentList
import com.example.kotlin_social_media_app.model.user_following.UserFollowingList
import com.example.kotlin_social_media_app.network.RetrofitService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FollowingRepo @Inject constructor(private val retrofitService: RetrofitService) {
    fun getUserFollowingFromApiCall(email: String, userList: MutableLiveData<UserFollowingList>) {
        retrofitService.getUserFollowing(email)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getUserFollowingFromApiCallObserverRx(userList))
    }

    private fun getUserFollowingFromApiCallObserverRx(userList: MutableLiveData<UserFollowingList>): Observer<UserFollowingList> {
        return object : Observer<UserFollowingList> {
            override fun onSubscribe(d: Disposable) {
                // start progress indicator
            }

            override fun onNext(t: UserFollowingList) {
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