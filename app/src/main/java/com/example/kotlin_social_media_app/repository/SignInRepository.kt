package com.example.kotlin_social_media_app.repository

import androidx.lifecycle.MutableLiveData
import com.example.kotlin_social_media_app.model.ExploreList
import com.example.kotlin_social_media_app.model.UserAuth
import com.example.kotlin_social_media_app.network.RetrofitService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SignInRepository @Inject constructor(private val retrofitService: RetrofitService) {
    fun createUserOrUpdateFromApiCall(email_user: String,userList: MutableLiveData<UserAuth>) {
        retrofitService.postUserOrUpdate(email_user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(Schedulers.io())
            .subscribe(createUserOrUpdateObserverRx(userList))
    }

    private fun createUserOrUpdateObserverRx(user: MutableLiveData<UserAuth>): Observer<UserAuth> {
        return object : Observer<UserAuth> {
            override fun onSubscribe(d: Disposable) {
                // start progress indicator
            }

            override fun onNext(t: UserAuth) {
                user.postValue(t)
            }

            override fun onError(e: Throwable) {
                user.postValue(null)
            }

            override fun onComplete() {
                // end progress indicator
            }
        }
    }
}