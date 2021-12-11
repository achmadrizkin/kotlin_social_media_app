package com.example.kotlin_social_media_app.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_social_media_app.model.reels.ReelsList
import com.example.kotlin_social_media_app.model.user_following.UserFollowingList
import com.example.kotlin_social_media_app.repository.CommentRepo
import com.example.kotlin_social_media_app.repository.FollowingRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FollowingActivityViewModel @Inject constructor(private val repository: FollowingRepo): ViewModel() {
    var userFollowingList: MutableLiveData<UserFollowingList>

    init {
        userFollowingList = MutableLiveData()
    }

    fun getFollowingByUserObservable(): MutableLiveData<UserFollowingList> {
        return userFollowingList
    }

    fun getFollowingByUserOfData(email: String) {
        repository.getUserFollowingFromApiCall(email, userFollowingList)
    }
}