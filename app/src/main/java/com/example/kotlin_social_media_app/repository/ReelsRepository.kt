package com.example.kotlin_social_media_app.repository

import androidx.lifecycle.MutableLiveData
import com.example.kotlin_social_media_app.model.reels.ReelsList
import com.example.kotlin_social_media_app.network.RetrofitService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ReelsRepository @Inject constructor(private val retrofitService: RetrofitService) {
    fun getReelsByNotEmailAndOrderByLikeFromApiCall(email: String, reelsList: MutableLiveData<ReelsList>) {
        retrofitService.getReelsByNotEmailAndOrderByLike(email)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getReelsListObserverRx(reelsList))
    }

    private fun getReelsListObserverRx(user: MutableLiveData<ReelsList>): Observer<ReelsList> {
        return object : Observer<ReelsList> {
            override fun onSubscribe(d: Disposable) {
                // start progress indicator
            }

            override fun onNext(t: ReelsList) {
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