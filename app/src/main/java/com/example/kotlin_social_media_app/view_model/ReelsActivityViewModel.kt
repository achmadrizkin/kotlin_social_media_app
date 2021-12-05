package com.example.kotlin_social_media_app.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_social_media_app.model.reels.ReelsList
import com.example.kotlin_social_media_app.repository.ReelsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReelsActivityViewModel @Inject constructor(private val repository: ReelsRepository): ViewModel() {

    var reelsList: MutableLiveData<ReelsList>

    init {
        reelsList = MutableLiveData()
    }

    fun getReelsByNotEmailAndOrderByLikeObservable(): MutableLiveData<ReelsList> {
        return reelsList
    }

    fun getReelsByNotEmailAndOrderByLikeOfData(name_user: String) {
        repository.getReelsByNotEmailAndOrderByLikeFromApiCall(name_user, reelsList)
    }


}