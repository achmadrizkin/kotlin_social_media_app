package com.example.kotlin_social_media_app.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_social_media_app.model.ExploreList
import com.example.kotlin_social_media_app.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserActivityViewModel @Inject constructor(private val repository: UserRepository): ViewModel() {
    lateinit var userExploreList: MutableLiveData<ExploreList>

    init {
        userExploreList = MutableLiveData()
    }

    fun getExploreObservable(): MutableLiveData<ExploreList> {
        return userExploreList
    }

    fun getExploreListOfData(email: String) {
        repository.getExploreByEmailFromApiCall(email, userExploreList)
    }
}