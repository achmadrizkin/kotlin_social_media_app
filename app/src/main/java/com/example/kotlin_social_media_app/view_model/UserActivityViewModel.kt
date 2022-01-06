package com.example.kotlin_social_media_app.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_social_media_app.model.explore.ExploreList
import com.example.kotlin_social_media_app.model.user.UserList
import com.example.kotlin_social_media_app.model.user_auth.UpdateUserPost
import com.example.kotlin_social_media_app.model.user_following.UserFollowing
import com.example.kotlin_social_media_app.repository.UserFollowingRepo
import com.example.kotlin_social_media_app.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserActivityViewModel @Inject constructor(private val repository: UserRepository, private val userFollowingRepo: UserFollowingRepo): ViewModel() {
    var userExploreList: MutableLiveData<ExploreList>
    var userEmailList: MutableLiveData<UserList>
    var updateUserFollowing: MutableLiveData<UpdateUserPost>
    var updateUserFollowers: MutableLiveData<UpdateUserPost>
    val userEmailList2: MutableLiveData<UserList>
    val postUserFollowing: MutableLiveData<UserFollowing>

    init {
        userExploreList = MutableLiveData()
        userEmailList = MutableLiveData()
        updateUserFollowing = MutableLiveData()
        userEmailList2 = MutableLiveData()
        updateUserFollowers = MutableLiveData()
        postUserFollowing = MutableLiveData()
    }

    fun getExploreObservable(): MutableLiveData<ExploreList> {
        return userExploreList
    }

    fun getUserByEmailObservable(): MutableLiveData<UserList> {
        return userEmailList
    }

    fun getUserByEmail2Observable(): MutableLiveData<UserList> {
        return userEmailList2
    }

    fun postUserFollowingObservable(): MutableLiveData<UserFollowing> {
        return postUserFollowing
    }

    fun getExploreListOfData(email: String) {
        repository.getExploreByEmailFromApiCall(email, userExploreList)
    }

    fun getUserListByEmailOfData(email: String) {
        repository.getUserByEmailFromApiCall(email, userEmailList)
    }

    fun getUserListByEmail2OfData(email: String) {
        repository.getUserByEmailFromApiCall(email, userEmailList2)
    }

    fun updateUserFollowingOfData(email: String, id: Int) {
        repository.updateUserFollowingFromApiCall(email, id, updateUserFollowing)
    }

    fun updateUserFollowersOfData(email: String, id: Int) {
        repository.updateUserFollowersFromApiCall(email, id, updateUserFollowing)
    }

    fun postUserFollowingOfData(userFollowing: UserFollowing) {
        repository.postUserFollowingFromApiCall(userFollowing, postUserFollowing)
    }
}