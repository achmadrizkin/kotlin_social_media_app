package com.example.kotlin_social_media_app.repository

import androidx.lifecycle.MutableLiveData
import com.example.kotlin_social_media_app.model.ExploreList
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
}