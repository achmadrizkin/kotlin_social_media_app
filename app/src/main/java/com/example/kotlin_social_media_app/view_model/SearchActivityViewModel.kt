package com.example.kotlin_social_media_app.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_social_media_app.model.UserList
import com.example.kotlin_social_media_app.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchActivityViewModel @Inject constructor(private val repository: SearchRepository): ViewModel() {
    lateinit var userList: MutableLiveData<UserList>

    init {
        userList = MutableLiveData()
    }

    fun getUserByNameObservable(): MutableLiveData<UserList> {
        return userList
    }

    fun searchUserListOfData(name_user: String) {
        repository.getBookFromApiCall(name_user, userList)
    }
}