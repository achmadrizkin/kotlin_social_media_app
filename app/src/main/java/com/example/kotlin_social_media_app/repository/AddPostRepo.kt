package com.example.kotlin_social_media_app.repository

import androidx.lifecycle.MutableLiveData
import com.example.kotlin_social_media_app.model.explore.Explore
import com.example.kotlin_social_media_app.model.user_auth.UpdateUserPost
import com.example.kotlin_social_media_app.network.RetrofitService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AddPostRepo @Inject constructor(private val retrofitService: RetrofitService) {
    fun postExploreFromApiCall(explore: Explore, userList: MutableLiveData<Explore>) {
        retrofitService.postExplore(explore)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(postExploreObserverRx(userList))
    }

    fun updateUserPostFromApiCall(email_user: String, id: Int, userList: MutableLiveData<UpdateUserPost>) {
        retrofitService.updateUserPost(email_user, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(updateUserPostObserverRx(userList))
    }


    private fun postExploreObserverRx(userList: MutableLiveData<Explore>): Observer<Explore> {
        return object : Observer<Explore> {
            override fun onSubscribe(d: Disposable) {
                // start progress indicator
            }

            override fun onNext(t: Explore) {
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

    private fun updateUserPostObserverRx(userList: MutableLiveData<UpdateUserPost>): Observer<UpdateUserPost> {
        return object : Observer<UpdateUserPost> {
            override fun onSubscribe(d: Disposable) {
                // start progress indicator
            }

            override fun onNext(t: UpdateUserPost) {
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