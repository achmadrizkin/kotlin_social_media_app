package com.example.kotlin_social_media_app.repository

import androidx.lifecycle.MutableLiveData
import com.example.kotlin_social_media_app.model.comment.CommentList
import com.example.kotlin_social_media_app.model.user.User
import com.example.kotlin_social_media_app.model.user_following.UserFollowingList
import com.example.kotlin_social_media_app.network.RetrofitService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EditProfileRepo @Inject constructor(private val retrofitService: RetrofitService) {
    fun updateUserProfileFromApiCallFromApiCall(id: String, user: User, userLiveData: MutableLiveData<User>) {
        retrofitService.updateUserProfile(id, user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(updateUserProfileObserverRx(userLiveData))
    }

    private fun updateUserProfileObserverRx(userList: MutableLiveData<User>): Observer<User> {
        return object : Observer<User> {
            override fun onSubscribe(d: Disposable) {
                // start progress indicator
            }

            override fun onNext(t: User) {
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