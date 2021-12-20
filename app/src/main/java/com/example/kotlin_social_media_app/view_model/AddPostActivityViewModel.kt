package com.example.kotlin_social_media_app.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_social_media_app.model.explore.Explore
import com.example.kotlin_social_media_app.model.user_auth.UpdateUserPost
import com.example.kotlin_social_media_app.model.user_following.UserFollowingList
import com.example.kotlin_social_media_app.repository.AddPostRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddPostActivityViewModel @Inject constructor(private val repository: AddPostRepo): ViewModel() {
    var exploreLiveData: MutableLiveData<Explore>
    var updateUserPost: MutableLiveData<UpdateUserPost>

    init {
        exploreLiveData = MutableLiveData()
        updateUserPost = MutableLiveData()
    }

    fun postExploreObservable(): MutableLiveData<Explore> {
        return exploreLiveData
    }

    fun updateUserPostObservable(): MutableLiveData<UpdateUserPost> {
        return updateUserPost
    }

    fun postExploreOfData(explore: Explore) {
        repository.postExploreFromApiCall(explore, exploreLiveData)
    }

    fun updateUserPostOfData(email_user: String, id: Int) {
        repository.updateUserPostFromApiCall(email_user, id,updateUserPost)
    }
}