package com.example.kotlin_social_media_app.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_social_media_app.model.explore.ExploreList
import com.example.kotlin_social_media_app.model.user_following.UserFollowingList
import com.example.kotlin_social_media_app.repository.FollowingRepo
import com.example.kotlin_social_media_app.repository.HomeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeActivityViewModel @Inject constructor(private val repository: HomeRepo): ViewModel() {
    var exploreList: MutableLiveData<ExploreList>

    init {
        exploreList = MutableLiveData()
    }

    fun getFollowingByUserObservable(): MutableLiveData<ExploreList> {
        return exploreList
    }

    fun getFollowingByUserOfData(email: String) {
        repository.getFollowingByEmailUserFromApiCall(email, exploreList)
    }
}