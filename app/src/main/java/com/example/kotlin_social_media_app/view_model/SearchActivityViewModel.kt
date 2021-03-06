package com.example.kotlin_social_media_app.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_social_media_app.model.explore.ExploreList
import com.example.kotlin_social_media_app.model.user.UserList
import com.example.kotlin_social_media_app.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchActivityViewModel @Inject constructor(private val repository: SearchRepository): ViewModel() {
    var userList: MutableLiveData<UserList>
    var exploreList: MutableLiveData<ExploreList>

    init {
        userList = MutableLiveData()
        exploreList = MutableLiveData()
    }

    fun getUserByNameObservable(): MutableLiveData<UserList> {
        return userList
    }

    fun getExploreObservable(): MutableLiveData<ExploreList> {
        return exploreList
    }

    fun searchUserListOfData(name_user: String) {
        repository.getUserByNameFromApiCall(name_user, userList)
    }

    fun getExploreListOfData(user: String) {
        repository.getExploreOrderByLikeFromApiCall(user, exploreList)
    }
}