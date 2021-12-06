package com.example.kotlin_social_media_app.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_social_media_app.model.explore.ExploreList
import com.example.kotlin_social_media_app.repository.PostDetailsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostDetailsActivityViewModel @Inject constructor(private val repository: PostDetailsRepo): ViewModel() {
    var exploreList: MutableLiveData<ExploreList>

    init {
        exploreList = MutableLiveData()
    }

    fun getExploreObservable(): MutableLiveData<ExploreList> {
        return exploreList
    }

    fun getExploreListOfData(user: String) {
        repository.getExploreOrderByLikeFromApiCall(user, exploreList)
    }
}