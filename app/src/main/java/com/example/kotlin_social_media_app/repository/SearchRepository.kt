package com.example.kotlin_social_media_app.repository

import androidx.lifecycle.MutableLiveData
import com.example.kotlin_social_media_app.model.ExploreList
import com.example.kotlin_social_media_app.model.UserList
import com.example.kotlin_social_media_app.network.RetrofitService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Flow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchRepository @Inject constructor(private val retrofitService: RetrofitService)  {
    fun getUserByNameFromApiCall(name_user: String, userList: MutableLiveData<UserList>) {
        retrofitService.searchUser(name_user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getUserListObserverRx(userList))
    }

    fun getExploreOrderByLikeFromApiCall(user: String, exploreList: MutableLiveData<ExploreList>) {
        retrofitService.getExploreOrderByLike(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getExploreListObserverRx(exploreList))
    }

    private fun getUserListObserverRx(userList: MutableLiveData<UserList>): Observer<UserList> {
        return object : Observer<UserList> {
            override fun onSubscribe(d: Disposable) {
                // start progress indicator
            }

            override fun onNext(t: UserList) {
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

    private fun getExploreListObserverRx(userList: MutableLiveData<ExploreList>): Observer<ExploreList> {
        return object : Observer<ExploreList> {
            override fun onSubscribe(d: Disposable) {
                // start progress indicator
            }

            override fun onNext(t: ExploreList) {
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