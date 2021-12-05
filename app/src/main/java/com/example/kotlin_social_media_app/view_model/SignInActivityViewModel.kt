package com.example.kotlin_social_media_app.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_social_media_app.model.user_auth.UserAuth
import com.example.kotlin_social_media_app.repository.SignInRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInActivityViewModel @Inject constructor(private val repository: SignInRepository): ViewModel() {
    var userAuth: MutableLiveData<UserAuth>

    init {
        userAuth = MutableLiveData()
    }

    fun createUserOrUpdateObservable(): MutableLiveData<UserAuth> {
        return userAuth
    }


    fun createUserOrUpdateOfData(email_user: String) {
        repository.createUserOrUpdateFromApiCall(email_user, userAuth)
    }

}