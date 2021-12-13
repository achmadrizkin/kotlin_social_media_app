package com.example.kotlin_social_media_app.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_social_media_app.model.explore.Explore
import com.example.kotlin_social_media_app.model.user_following.UserFollowingList
import com.example.kotlin_social_media_app.repository.AddPostRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddPostActivityViewModel @Inject constructor(private val repository: AddPostRepo): ViewModel() {
    var exploreLiveData: MutableLiveData<Explore>

    init {
        exploreLiveData = MutableLiveData()
    }

    fun postExploreObservable(): MutableLiveData<Explore> {
        return exploreLiveData
    }

    fun postExploreOfData(explore: Explore) {
        repository.postExploreFromApiCall(explore, exploreLiveData)
    }
}