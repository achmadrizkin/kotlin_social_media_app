package com.example.kotlin_social_media_app.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_social_media_app.model.comment.Comment
import com.example.kotlin_social_media_app.model.comment.CommentList
import com.example.kotlin_social_media_app.model.user.User
import com.example.kotlin_social_media_app.repository.EditProfileRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProfileActivityViewModel @Inject constructor(private val repository: EditProfileRepo): ViewModel() {

    var userLiveData: MutableLiveData<User>

    init {
        userLiveData = MutableLiveData()
    }

    fun updateUserProfileObservable(): MutableLiveData<User> {
        return userLiveData
    }

    fun updateUserProfileOfData(postUser: String, user: User) {
        repository.updateUserProfileFromApiCallFromApiCall(postUser, user, userLiveData)
    }
}