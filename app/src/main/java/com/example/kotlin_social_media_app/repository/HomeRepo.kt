package com.example.kotlin_social_media_app.repository

import androidx.lifecycle.MutableLiveData
import com.example.kotlin_social_media_app.model.explore.ExploreList
import com.example.kotlin_social_media_app.model.user_following.UserFollowingList
import com.example.kotlin_social_media_app.network.RetrofitService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeRepo @Inject constructor(private val retrofitService: RetrofitService) {
    fun getFollowingByEmailUserFromApiCall(email: String, exploreList: MutableLiveData<ExploreList>) {
        retrofitService.getFollowingByEmailUser(email)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getFollowingByEmailUserObserverRx(exploreList))
    }

    private fun getFollowingByEmailUserObserverRx(userList: MutableLiveData<ExploreList>): Observer<ExploreList> {
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