package com.example.kotlin_social_media_app.repository

import androidx.lifecycle.MutableLiveData
import com.example.kotlin_social_media_app.model.explore.ExploreList
import com.example.kotlin_social_media_app.model.user.UserList
import com.example.kotlin_social_media_app.model.user_auth.UpdateUserPost
import com.example.kotlin_social_media_app.network.RetrofitService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserRepository @Inject constructor(private val retrofitService: RetrofitService) {
    fun getExploreByEmailFromApiCall(email: String, exploreList: MutableLiveData<ExploreList>) {
        retrofitService.getExploreByEmailAndOrderByCreateAt(email)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getExploreByEmailObserverRx(exploreList))
    }

    fun getUserByEmailFromApiCall(email: String, userList: MutableLiveData<UserList>) {
        retrofitService.getUserByEmail(email)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getUserByEmailObserverRx(userList))
    }

    fun updateUserFollowingFromApiCall(email: String, id: Int, userList: MutableLiveData<UpdateUserPost>) {
        retrofitService.updateUserFollowing(email, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(updateUserFollowingObserverRx(userList))
    }

    fun updateUserFollowersFromApiCall(email: String, id: Int, userList: MutableLiveData<UpdateUserPost>) {
        retrofitService.updateUserFollowers(email, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(updateUserFollowersObserverRx(userList))
    }

    private fun getExploreByEmailObserverRx(exploreList: MutableLiveData<ExploreList>): Observer<ExploreList> {
        return object : Observer<ExploreList> {
            override fun onSubscribe(d: Disposable) {
                // start progress indicator
            }

            override fun onNext(t: ExploreList) {
                exploreList.postValue(t)
            }

            override fun onError(e: Throwable) {
                exploreList.postValue(null)
            }

            override fun onComplete() {
                // end progress indicator
            }
        }
    }

    private fun getUserByEmailObserverRx(userList: MutableLiveData<UserList>): Observer<UserList> {
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

    private fun updateUserFollowingObserverRx(userList: MutableLiveData<UpdateUserPost>): Observer<UpdateUserPost> {
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

    private fun updateUserFollowersObserverRx(userList: MutableLiveData<UpdateUserPost>): Observer<UpdateUserPost> {
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